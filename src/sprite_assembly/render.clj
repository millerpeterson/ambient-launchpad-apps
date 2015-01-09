(ns sprite-assembly.render
  (require [sprite-assembly.sprite :as sprite]))

(use 'overtone.osc)

(defn client
  ([host port]
    (osc-client host port))
  ([]
    (osc-client "localhost" 1337)))

(defn update-led
  [cli ^Integer row ^Integer col color]
  (let [[^Integer red ^Intereger green] color]
    (osc-send cli "/phi/launchpad/out/led" row col red green)))

(defn show
  [cli]
  (osc-send cli "/phi/launchpad/out/render" (str 1)))

(defn brightness
  [value cli]
  (osc-send cli "/phi/launchpad/brightness" (str value)))

(defn draw
  [spr cli]
  (doseq [led-pos (keys spr)]
    (let [[row col] led-pos
          colr (get spr led-pos)]
      (update-led cli row col colr))))


