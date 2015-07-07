(ns sprite-assembly.mode-control
  (:require [clojure.set :as set]
            [sprite-assembly.buttons :as btn]))

(def mode-change-buttons
  "Modes mapping to the button position that changes to them."
  {:assembly [0 4]
   :frames [0 5]
   :draw [0 6]
   :palette [0 7]})

(def mode-change-positions
  "Button positions that change mode."
  (into #{} (vals mode-change-buttons)))

(defn mode-for-position
  "Mode that corresponds to a given mode-change button position."
  [position]
  (let [[row col] position]
    ((set/map-invert mode-change-buttons) [row col])))

(defn change-mode
  "Return a model with its mode changed to new-mode."
  [new-mode model]
  (assoc model :active-mode new-mode))

(defn handler
  "Change mode when a mode-change button is pressed."
  [event model]
  (let [[row col _] event]
    (cond (and (btn/press? event)
               (contains? mode-change-positions [row col]))
          (change-mode (mode-for-position [row col]) model)
          :else model)))

