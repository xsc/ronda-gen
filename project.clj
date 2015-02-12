(defproject ronda/gen "0.1.0-SNAPSHOT"
  :description "test.check generators for ronda schemas (and others)."
  :url "https://github.com/xsc/ronda-gen"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0-alpha5"]
                 [org.clojure/test.check "0.7.0"]
                 [potemkin "0.3.11"]
                 [prismatic/schema "0.3.7"]]
  :pedantic? :abort)
