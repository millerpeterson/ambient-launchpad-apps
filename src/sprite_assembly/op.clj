(ns sprite-assembly.op
  (require [sprite-assembly.sprite :as sprite]))

(defn region-map
  "Map leds in a given region of a sprite."
  [spr region func]
  (reduce #(assoc %1 %2 (func %2 (get %1 %2)))
          spr region))
