(ns ronda.gen.protocols
  (:require [potemkin :refer [defprotocol+]]))

(defprotocol+ GeneratorSchema
  "Protocol for schemas that can be converted to a generator."
  (generator* [this f]
    "Convert schema to test.check generator. `f` is a function
     that can be used to create sub-generators."))
