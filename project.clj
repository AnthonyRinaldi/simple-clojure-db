(defproject myApp "1.0.0-SNAPSHOT"
  :description "myApp"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.immutant/immutant "2.0.0-beta2"]
                 [compojure "1.3.1"]
                 [ring/ring-devel "1.3.1"]
                 [org.clojure/core.memoize "0.5.6"]
                 [clj-time "0.9.0"]
                 [cheshire "5.4.0"]
                 [environ "1.0.0"]]
  :repositories [["Immutant incremental builds"
                  "http://downloads.immutant.org/incremental/"]]
  :plugins [[lein-immutant "2.0.0-beta1"]]
  :main myApp.core
  :uberjar-name "myApp-standalone.jar"
  :profiles {:uberjar {:aot [myApp.core]}}
  :min-lein-version "2.4.0")
