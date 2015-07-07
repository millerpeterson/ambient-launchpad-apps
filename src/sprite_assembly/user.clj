(ns sprite-assembly.user
  (require
    [clojure.set :as set]
    [sprite-assembly.osc :as osc]
    [sprite-assembly.buttons :as btn]
    [sprite-assembly.sprite :as spr]
    [sprite-assembly.render :as render]
    [sprite-assembly.op :as sop]
    [sprite-assembly.led :as led]))

;(def ser (osc/server))
;(def cli (osc/client))
;
;(def init-model {:active-mode :draw
;                 :sprite (spr/blank)})
;
;(defn model-agent (agent init-model))
;
;(defn change-mode!
;  [new-mode model]
;  (assoc model :active-mode new-mode)
;

;
;(defn render-active-mode-indicator
;  []
;  (let [[r c] (mode-change-buttons (@model :active-mode))]
;    (render/update-led cli r c (led/named :green))))
;
;(def modes {:global {:handler mode-change-handler
;                     :renderer render-active-mode-indicator}})
;
;(defn handle-input
;  [model event]
;  (when (btn/press? event)
;    (doseq [[_ mode] modes]
;      (let [updated-model ((mode :handler) event model)]
;        ((mode :renderer) updated-model cli)))
;    (render/show cli)
;    updated-model))
;
;(btn/handle-presses ser (fn [event]
;                          (send-off model-agent handle-input event)))