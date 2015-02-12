(ns ^:no-doc ronda.gen.schemas
  (:require [ronda.gen.protocols :as g]
            [clojure.test.check.generators :as gen]
            [schema.core :as s]))

(extend-protocol g/GeneratorSchema
  schema.core.Maybe
  (generator* [{:keys [schema]} f]
    (gen/one-of
      [(gen/return nil)
       (f schema)]))

  schema.core.EqSchema
  (generator* [{:keys [v]} f]
    (f v))

  schema.core.Either
  (generator* [{:keys [schemas]} f]
    (gen/one-of (map f schemas)))

  schema.core.EnumSchema
  (generator* [{:keys [vs]} f]
    (gen/elements (vec vs)))

  schema.core.NamedSchema
  (generator* [{:keys [schema]} f]
    (f schema))

  schema.core.Predicate
  (generator* [{:keys [p?]} f]
    (gen/such-that p? gen/any)))
