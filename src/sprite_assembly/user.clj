(ns sprite-assembly.user
  (require [sprite-assembly.osc :as osc]
    [sprite-assembly.buttons :as btn]
    [sprite-assembly.sprite :as spr]
    [sprite-assembly.render :as render]
    [sprite-assembly.op :as sop]
    [sprite-assembly.led :as led]))

(def ser (osc/server))
(def cli (osc/client))

(def model (ref (spr/blank)))

(defn random-paint [spr row col]
  (sop/set-led spr row col (led/random)))

(btn/handle-press ser
                  (fn [[row col _]]
                    (dosync
                      (alter model
                             (fn [m] (random-paint m row col)))
                      (render/draw @model cli)
                      (render/show cli)
                      (println row col))))