(ns sprite-assembly.launchpad
  (require
    [clojure.math.combinatorics :as combo]
    [overtone.osc :as tone]))

(def nrows
  "Number of rows on the launchpad."
  9)

(def ncols
  "Number of coluns on the launchpad."
  9)

(defn positions
  "Possible launchpad positions."
  []
  (into #{} (combo/cartesian-product (range nrows) (range ncols))))

(defn update-led
  "Set a launchpad LED (buffered not shown until call to show)."
  [[row col] [red green] cli]
    (tone/osc-send cli "lpad/led"
              (Integer. row) (Integer. col)
              (Integer. red) (Integer. green)))

(defn show
  "Show all buffered LED updates."
  [cli]
  (tone/osc-send cli "lpad/show" (Integer. 1)))

(defn brightness
  "Set launchpad brightness (0-127)."
  [value cli]
  (tone/osc-send cli "lpad/brightness" (Integer. value)))

(defn draw
  "Draw a sprite on the launchpad."
  [spr cli]
  (doseq [led-pos (keys spr)]
    (update-led led-pos (get spr led-pos) cli)))

(defn clear
  "Set all launchpad LED's to blank (buffered)."
  [cli]
  (doseq [led-pos (positions)]
    (update-led led-pos [0 0] cli)))