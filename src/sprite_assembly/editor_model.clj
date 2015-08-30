(ns sprite-assembly.editor-model
  (:require [sprite-assembly.sprite :as spr]))

(defn build
  []
  {:active-mode :draw
   :sprite (spr/blank)})