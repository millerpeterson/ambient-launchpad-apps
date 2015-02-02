(ns sprite-assembly.render
  (require [sprite-assembly.sprite :as sprite]))

(use 'overtone.osc)

(defn update-led
  [cli row col color]
  (let [[red green] color]
    (osc-send cli "launchpad/led"
              (Integer. row) (Integer. col)
              (Integer. red) (Integer. green))))

(defn show
  [cli]
  (osc-send cli "launchpad/render" (Integer. 1)))

(defn brightness
  [value cli]
  (osc-send cli "launchpad/brightness" (Integer. value)))

(defn draw
  [spr cli]
  (doseq [led-pos (keys spr)]
    (let [[row col] led-pos
          colr (get spr led-pos)]
      (update-led cli row col colr))))


