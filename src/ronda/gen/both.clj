(ns ^:no-doc ronda.gen.both
  (:require [ronda.gen.protocols :as g]
            [clojure.test.check.generators :as gen]
            [schema.core :as s]))

;; ## Helpers

(defn- pred?
  "Is the given value a predicate schema."
  [v]
  (and (instance? schema.core.Predicate v)
       (not= v s/Int)))

(defn- make-predicate
  "Generate predicate function as a AND combination of
   all predicate schemas."
  [schemas]
  (if-let [fs (->> (filter pred? schemas)
                   (map :p?)
                   (seq))]
    (fn [v]
      (every? #(% v) fs))
    (constantly true)))

(defn- make-generator
  "Create generator by taking first non-predicate schema."
  [schemas f]
  (or (->> (remove pred? schemas)
           (map f)
           (first))
      gen/any))

;; ## Generator

(extend-protocol g/GeneratorSchema
  schema.core.Both
  (generator* [{:keys [schemas] :as b} f]
    (let [gen (make-generator schemas f)
          predicate (make-predicate schemas)]
      (gen/such-that
        #(and (predicate %) (nil? (s/check b %)))
        gen
        100))))
