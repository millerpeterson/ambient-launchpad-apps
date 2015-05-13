(ns sprite-assembly.render
  (require
    [clojure.math.combinatorics :as combo]
    [sprite-assembly.sprite :as sprite]))

(use 'overtone.osc)

(defn update-led
  [cli row col color]
  (let [[red green] color]
    (osc-send cli "lpad/led"
              (Integer. row) (Integer. col)
              (Integer. red) (Integer. green))))

(defn show
  [cli]
  (osc-send cli "lpad/show" (Integer. 1)))

(defn brightness
  [value cli]
  (osc-send cli "lpad/brightness" (Integer. value)))

(defn draw
  [spr cli]
  (doseq [led-pos (keys spr)]
    (let [[row col] led-pos
          colr (get spr led-pos)]
      (update-led cli row col colr))))

(defn clear
  [cli]
  (doseq [[row col] (combo/cartesian-product (range 9) (range 9))]
    (update-led cli row col [0 0])))