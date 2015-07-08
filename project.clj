(defproject sprite-assembly "0.1.0-SNAPSHOT"
  :description "Sprite Assembly for Launchpad"
  :url "http://coming.soon"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [environ "1.0.0"]
                 [overtone/osc-clj "0.8.1"]
                 [org.clojure/math.combinatorics "0.0.8"]
                 [clj-stacktrace "0.2.8"]
                 [speclj "3.3.0"]]
  :repl-options {:welcome (println "> Sprite Assembler")
                 :init-ns sprite-assembly.core
                 :caught clj-stacktrace.repl/pst+}
  :profiles {:dev {:plugins [[lein-environ "1.0.0"]
                             [speclj "3.3.0"]]
                   :test-paths ["spec"]
                   :env {}}})
