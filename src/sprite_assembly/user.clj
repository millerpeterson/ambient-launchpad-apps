(ns sprite-assembly.user
  (require [sprite-assembly.osc :as osc]
    [sprite-assembly.buttons :as btn]))

(def ser (osc/server))
(def cli (osc/client))

(btn/handle-press ser "/test"
                  (fn [msg] (println msg)))