# ronda-gen

__ronda-gen__ offers [test.check][test-check] generators based on
[prismatic/schema][schema].

See [schema-gen][schema-gen] for a similar (and in parts surely identical)
approach.

## Usage

Don't.

```clojure
(require '[ronda.gen :as r]
         '[schema.core :as s])

(r/sample
  {:first-name s/Str
   :last-name  s/Str
   :age        (s/both s/Int (s/pred #(>= % 5)))}
  5)
;; => ({:first-name "", :last-name "", :age 18}
;;     {:first-name "", :last-name "S", :age 23}
;;     {:first-name "", :last-name "", :age 18}
;;     {:first-name "", :last-name "CgF", :age 21}
;;     {:first-name "s", :last-name "aRkl", :age 22})

(r/generator {:x s/Int})
;; => #clojure.test.check.generators.Generator{:gen ...}
```

## License

Copyright &copy; 2015 Yannick Scherer

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

[schema]: https://github.com/prismatic/schema
[test-check]: https://github.com/clojure/test.check
[schema-gen]: https://github.com/zeeshanlakhani/schema-gen
