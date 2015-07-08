(ns sprite-assembly.core-spec
  (require [speclj.core :refer :all]
           [sprite-assembly.core :refer :all]
           [sprite-assembly.editor-model :as model]))

(defn stubbed-controller
  []
  {:global {:handler (stub :global-handler)
            :renderer (stub :global-renderer)}
   :mode1 {:handler (stub :mode1-handler)
           :renderer (stub :mode1-renderer)}
   :mode2 {:handler (stub :mode2-handler)
           :renderer (stub :mode2-renderer)}})

(defn dummy-controller
  []
  {:global {:handler :global-handler
            :renderer :global-renderer}
   :mode1 {:handler :mode1-handler
           :renderer :mode1-renderer}
   :mode2 {:handler :mode2-handler
           :renderer :mode2-renderer}})

(defn dummy-app
  []
  {:model (model/build)
   :controller (dummy-controller)
   :lpad {}})

(defn stubbed-app
  []
  {:model (model/build)
   :controller (stubbed-controller)
   :lpad {}})

(defn should-be-an-app
  [a]
  (do
    (should-contain :model a)
    (should-contain :controller a)
    (should-contain :lpad a)))

(describe "handlers"
          (with-stubs)
          (it "returns the mode handlers"
              (let [hs (handlers (dummy-app))]
                (should= 3 (count hs))
                (should-contain :global-handler hs)
                (should-contain :mode1-handler hs)
                (should-contain :mode2-handler hs))))

(describe "renderers"
          (with-stubs)
          (it "returns the mode renderers"
              (let [rs (renderers (dummy-app))]
                (should= 3 (count rs))
                (should-contain :global-renderer rs)
                (should-contain :mode1-renderer rs)
                (should-contain :mode2-renderer rs))))

(describe "handle-input!"
          (with-stubs)
          (with event [4 2 1])
          (with stubb-app (stubbed-app))

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

