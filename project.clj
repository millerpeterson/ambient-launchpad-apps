(defproject sprite-assembly "0.1.0-SNAPSHOT"
  :description "Sprite Assembly for Launchpad"
  :url "http://coming.soon"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [environ "1.0.0"]
                 [overtone/osc-clj "0.8.1"]
                 [org.clojure/math.combinatorics "0.0.8"]]
  :profiles {:dev {:plugins [[lein-environ "1.0.0"]]
                   :env {}}})
