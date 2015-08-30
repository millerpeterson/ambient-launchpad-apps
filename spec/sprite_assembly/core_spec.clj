(ns sprite-assembly.core-spec
  (require [speclj.core :refer :all]
           [speclj.run.standard]
           [sprite-assembly.core :refer :all]
           [sprite-assembly.editor-model :as model]
           [sprite-assembly.controller :as controller])
  (:import (sprite_assembly.controller DummyMode ControlMode)))

(defn controller
  [modes mode-generating-func]
  (reduce (fn [controller mode-name]
                  (assoc controller mode-name (mode-generating-func mode-name)))
                {} modes))

(defrecord StubbedMode
  [mode-name]
  ControlMode
  (handle-event [event app model] ((stub (keyword (str (name mode-name) "-handler"))
                                           {:return model}) event app model))
  (render [_ _] (stub (keyword (str (name mode-name) "-renderer")))))

(defn dummy-controller
  [modes]
  (controller modes (fn [_] (DummyMode.))))

(defn stubbed-controller
  [modes]
  (controller modes #(StubbedMode. %1)))

(defn app-with-controller
  [controller]
  {:model (model/build)
   :controller controller
   :lpad {}})

(defn dummy-app
  [modes]
  (app-with-controller (dummy-controller modes)))

(defn stubbed-app
  [modes]
  (app-with-controller (stubbed-controller modes)))

(defn should-be-an-app
  [a]
  (do
    (should-contain :model a)
    (should-contain :controller a)
    (should-contain :lpad a)))

(describe "handle-input!"
          (with-stubs)
          (with event [[4 2] 1])
          (with stubb-app (stubbed-app [:global :mode1 :mode2]))

          (it "returns an app"
              (let [app-after-event-handled (handle-input! @event @stubb-app)]
                (should-be-an-app app-after-event-handled)))

          (it "calls run-handlers"
              (with-redefs [run-handlers (stub :run-handlers)]
                (handle-input! @event @stubb-app)
                (should-have-invoked :run-handlers
                                     {:with [@event @stubb-app] :times 1})))

          (it "calls render!"
              (with-redefs [render! (stub :render!)]
                (handle-input! @event @stubb-app)
                (should-have-invoked :render!
                                     {:with [@stubb-app] :times 1}))))

(describe "run-handlers"
          (with-stubs)
          (with event [[2 3] 1])
          (with stub-app (stubbed-app [:global :draw :palette]))

          (it "invokes the global handler"
              (run-handlers @event @stub-app)
              (should-have-invoked :global-handler)))