(ns sprite-assembly.core
  (:require [sprite-assembly.editor-model :as model]
            [sprite-assembly.controller :as controller]
            [sprite-assembly.launchpad :as launchpad]))

(def app (ref {:model (agent (model/build))
               :controller (controller/build)
               :lpad (launchpad/client)}))

(defn render!
  [app])

(defn run-handlers
  [event app]
  (let [global-handled (controller/handle-event (:global (:controller app))
                                                event
                                                (:model app))]
    (assoc app :model global-handled)))

(defn handle-input!
  [event app]
  (let [app-after-handled (run-handlers event app)]
    (render! app)
    app-after-handled))

;(defn register-button-callback
;  )

;
;(btn/handle-presses ser (fn [event]
;                          (send-off model-agent handle-input event)))