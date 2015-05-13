(ns sprite-assembly.user
  (require
    [clojure.set :as set]
    [sprite-assembly.osc :as osc]
    [sprite-assembly.buttons :as btn]
    [sprite-assembly.sprite :as spr]
    [sprite-assembly.render :as render]
    [sprite-assembly.op :as sop]
    [sprite-assembly.led :as led]))

(def ser (osc/server))
(def cli (osc/client))

(def model (ref {:active-mode :draw
                 :sprite (spr/blank)}))

(defn change-mode!
  [new-mode]
  (alter model
         (fn [m] (assoc m :active-mode new-mode))))

(def mode-change-buttons
  {:assembly [0 4]
   :frames [0 5]
   :draw [0 6]
   :palette [0 7]})

(defn mode-change-handler
  [[row col _]]
  (cond (contains? (into #{} (vals mode-change-buttons)) [row col])
        (change-mode! ((set/map-invert mode-change-buttons) [row col]))))

(defn render-active-mode-indicator
  []
  (let [[r c] (mode-change-buttons (@model :active-mode))]
    (render/update-led cli r c (led/named :green))))

(def modes {:global {:handler mode-change-handler
                     :renderer render-active-mode-indicator}})

(defn raw-input-handler
  [event]
  (when (btn/press? event)
    (dosync
      (doseq [[_ mode] modes]
        ((mode :handler) event)
        ((mode :renderer)))
      (render/show cli)
      (println (:active-mode @model)))))

(btn/handle-presses ser raw-input-handler)