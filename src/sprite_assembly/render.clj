(ns sprite-assembly.render
  (:require [sprite-assembly.colors :as led]))

(defn render-active-mode-indicator
  [model cli]
  (launchpad/update-led (mode-control/position-for-mode (model :active-mode))
                        (led/named :green)
                        cli)

(defn render-active-mode
  [model control cli])

(defn render-global
  [model control cli]
  (render-active-mode-indicator model cli))