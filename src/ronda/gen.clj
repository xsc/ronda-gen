(ns ronda.gen
  (:require [ronda.gen
             [protocols :refer [generator*]]
             [simple :refer [simple-generator]]
             both map schemas seq]
            [clojure.test.check.generators :as gen]))

(defn generator
  "Create generator for the given schema."
  [schema]
  (or (simple-generator schema)
      (generator* schema generator)
      (throw
        (IllegalArgumentException.
          (format "cannot create generator from: %s"
                  (pr-str schema))))))

(defn sample
  "Generate samples for the given schema."
  [schema & args]
  (apply gen/sample (generator schema) args))
