(ns sprite-assembly.render
  (require [sprite-assembly.sprite :as sprite]))

(use 'overtone.osc)

(def osc-port 1337)

(def client (osc-client "localhost" osc-port))

(defn update-led
  [cli row col red green]
  (osc-send cli "/phi/launchpad/out/led"
            (str row) (str col)
            (str red) (str green)))

(defn show
  [cli]
  (osc-send cli "/phi/launchpad/out/render" (str 1)))

(defn brightness
  [cli value]
  (osc-send cli "/phi/launchpad/brightness" (str value)))

(defn draw
  [cli spr]
  (doseq [led-pos (keys spr)]
    (let [[row col] led-pos
          {red :r green :g} (get spr led-pos)]
      (update-led cli row col red green))))

