(ns sprite-assembly.mode-control
  (:require [clojure.set :as set]
            [sprite-assembly.buttons :as btn]))

(def mode-change-buttons
  {:assembly [0 4]
   :frames [0 5]
   :draw [0 6]
   :palette [0 7]})

(def mode-change-positions
  (into #{} (vals mode-change-buttons)))

(defn change-mode
  [new-mode model]
  (assoc model :active-mode new-mode))

(defn handler
  [event model]
  (let [[row col _] event]
    (cond (and (btn/press? event)
               (contains? mode-change-positions [row col]))
          (let [new-mode ((set/map-invert mode-change-buttons) [row col])]
            (change-mode new-mode model))
          :else model)))

