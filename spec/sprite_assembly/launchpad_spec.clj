(ns sprite-assembly.launchpad-spec
  (:require [speclj.core :refer :all]
            [sprite-assembly.launchpad :refer :all]
            [overtone.osc :as tone]))

(def cli {:mock :cli})

(describe "show"
          (with-stubs)
          (it "should send OSC - path: lpad/show, message: 1"
              (with-redefs [tone/osc-send (stub :osc-send)]
                (show cli)
                (should-have-invoked :osc-send
                                     {:with [cli "lpad/show" (Integer. 1)]}))))

(describe "update-led"
          (with-stubs)
          (it "should send OSC - path: lpad/led, message: <row> <col> <red> <green>"
              (with-redefs [tone/osc-send (stub :osc-send)]
                (update-led [5 6] [2 0] cli)
                (should-have-invoked :osc-send
                                     {:with [cli "lpad/led"
                                             (Integer. 5) (Integer. 6) (Integer. 2) (Integer. 0)]}))))

(describe "brighness"
          (with-stubs)
          (it "should send OSC - path: lpad/brightness, message: <brightness value>"
              (with-redefs [tone/osc-send (stub :osc-send)]
                (brightness 23 cli)
                (should-have-invoked :osc-send
                                     {:with [cli "lpad/brightness" (Integer. 23)]}))))

(describe "clear"
          (with-stubs)
          (it "should send a blank led message for each sprite position"
              (with-redefs [update-led (stub :update-led)]
                (clear cli)
                (doseq [pos (positions)]
                  (should-have-invoked :update-led
                                       {:with [(vec pos) [0 0] cli] :times 1})))))