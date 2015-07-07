(ns sprite-assembly.controller)

(defprotocol ControlMode
  (handle-event [event model] "Handle an incoming interaction event.")
  (render [model cli] "Display the mode view."))

(deftype DummyMode
  []
  ControlMode
  (handle-event [_ model] model)
  (render [_ _]))

(defn- global
  []
  (DummyMode.))

(defn- modes
  []
  {:draw (DummyMode.)
   :palette (DummyMode.)
   :frames (DummyMode.)
   :assembly (DummyMode.)})

(defn build
  "Constructs the handlers / renderers structure."
  []
  {:global (global)
   :modes (modes)})