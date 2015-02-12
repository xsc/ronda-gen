(ns ^:no-doc ronda.gen.map
  (:require [ronda.gen.protocols :as g]
            [clojure.test.check.generators :as gen]
            [schema.core :as s]))

(def ^:private no-key ::none)

(extend-protocol g/GeneratorSchema
  schema.core.RequiredKey
  (generator* [{:keys [k]} _]
    (gen/return k))

  schema.core.OptionalKey
  (generator* [{:keys [k]} _]
    (gen/elements [k no-key]))

  clojure.lang.Keyword
  (generator* [k _]
    (gen/return k))

  clojure.lang.IPersistentMap
  (generator* [m f]
    (->> (for [[k v] m
               :let [key-gen (f k)
                     val-gen (f v)]]
           (gen/tuple key-gen val-gen))
         (apply gen/tuple)
         (gen/fmap
           (fn [pairs]
             (into {} (remove #(= no-key (first %)) pairs) ))))))
