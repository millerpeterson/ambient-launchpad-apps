(ns sprite-assembly.buttons)

(use 'overtone.osc)

(defn handle-press [srv callback]
  (osc-handle srv "/btn"
              (fn [msg] (callback (:args msg)))))