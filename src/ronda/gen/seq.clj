(ns ^:no-doc ronda.gen.seq
  (:require [ronda.gen.protocols :as g]
            [clojure.test.check.generators :as gen]
            [schema.core :as s]))

;; ## Helpers

(defn- prepare-elements
  "Group seq elements into required, optional and rest schemas,
   and create generators."
  [sq f]
  (reduce
    (fn [m v]
      (let [k (if (instance? schema.core.One v)
                (if (:optional? v)
                  :optional
                  :required)
                :rest)
          g (if (= k :rest)
              (gen/vector (f v))
              (f (:schema v)))]
        (update-in m [k] (fnil conj []) g)))
    {} sq))

(defn- required-only-generator
  "Create generator for only required elements."
  [{:keys [required]}]
  (when (seq required)
    (apply gen/tuple required)))

(defn- required-and-optional-generator
  "Create generator for required elements and any prefix of the optional ones."
  [{:keys [required optional]}]
  (when (seq optional)
    (loop [prefixes []
           optional optional]
      (if (seq optional)
        (recur (conj prefixes optional) (butlast optional))
        (gen/one-of
          (for [prefix prefixes]
            (apply gen/tuple (concat required prefix))))))))

(defn- all-generator
  "Create generator for a combination of required, optional and rest
   parameters."
  [{:keys [required optional rest]}]
  (when (seq rest)
    (->> (gen/tuple
           (apply gen/tuple (concat required optional))
           (first rest))
         (gen/fmap (comp vec #(apply concat %))))))

;; ## Generator

(extend-protocol g/GeneratorSchema
  clojure.lang.Sequential
  (generator* [sq f]
    (let [data (prepare-elements sq f)]
      (gen/one-of
        (->> (vector
               (required-only-generator data)
               (required-and-optional-generator data)
               (all-generator data))
             (filter identity))))))
