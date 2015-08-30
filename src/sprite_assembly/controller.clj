(ns sprite-assembly.controller)

(defprotocol ControlMode
  (handle-event [m event model] "Handle an incoming interaction event.")
  (render [m model] "Display the mode view."))

(defrecord DummyMode
  []
  ControlMode
  (handle-event [_ _ model] model)
  (render [_ _]))

(defn build
  "Constructs the handlers / renderers structure."
  []
  {:global (DummyMode.)
   :draw (DummyMode.)
   :palette (DummyMode.)
   :frames (DummyMode.)
   :assembly (DummyMode.)})