(ns sprite-assembly.core-spec
  (require [speclj.core :refer :all]
           [speclj.run.standard]
           [sprite-assembly.core :refer :all]
           [sprite-assembly.editor-model :as model]))

(defn controller-mode
  [mode-name func-gen]
  {:handler (func-gen (keyword (str (name mode-name) "-handler")))
   :renderer (func-gen (keyword (str (name mode-name) "-renderer")))})

(defn controller
  [modes func-gen]
  (reduce (fn [controller mode-name]
            (assoc controller mode-name (controller-mode mode-name func-gen)))
          {}
          modes))

(defn dummy-controller
  [modes]
  (controller modes #(keyword %)))

(defn stubbed-controller
  [modes]
  (controller modes #(stub %)))

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

(describe "handlers"
          (with-stubs)
          (it "returns the mode handlers"
              (let [hs (handlers (dummy-app [:global :mode1 :mode2]))]
                (should= 3 (count hs))
                (should-contain :global-handler hs)
                (should-contain :mode1-handler hs)
                (should-contain :mode2-handler hs))))

(describe "renderers"
          (with-stubs)
          (it "returns the mode renderers"
              (let [rs (renderers (dummy-app [:global :mode1 :mode2]))]
                (should= 3 (count rs))
                (should-contain :global-renderer rs)
                (should-contain :mode1-renderer rs)
                (should-contain :mode2-renderer rs))))

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
          (with stub-app (stubbed-app [:global :mode1 :mode2]))

          (it "invokes all handlers"
              (run-handlers @event @stub-app)
              (should-have-invoked :global-handler)
              (should-have-invoked :mode1-handler)
              (should-have-invoked :mode2-handler)))