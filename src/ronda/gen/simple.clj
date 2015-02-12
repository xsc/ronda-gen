(ns ^:no-doc ronda.gen.simple
  (:require [clojure.test.check.generators :as gen]
            [schema.core :as s]))

(def gen-fast-keyword
  (gen/fmap keyword gen/string-alphanumeric))

(def simple-generator
  "Create simple, constant-value-based generator."
  {s/Bool    (gen/elements [true false])
   s/Int     gen/int
   Long      gen/int
   s/Keyword gen-fast-keyword
   s/Num     (gen/fmap double gen/ratio)
   Double    (gen/fmap double gen/ratio)
   s/Str     gen/string-alphanumeric
   s/Any     gen/any
   s/Symbol  gen/symbol})
