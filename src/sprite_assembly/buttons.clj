(ns sprite-assembly.buttons)

(use 'overtone.osc)

(defn handle-press [srv route callback]
  (osc-handle srv route callback))