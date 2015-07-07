(ns sprite-assembly.launchpad-spec
  (:require [speclj.core :refer :all]
            [sprite-assembly.launchpad :refer :all]
            [overtone.osc :as tone])
  (:import (sprite_assembly.launchpad OscLaunchpadOutput)))

(def cli {:mock :cli})

(describe "show"
          (with-stubs)
          (with lpad (OscLaunchpadOutput. cli))
          (it "should send OSC - path: lpad/show, message: 1"
              (with-redefs [tone/osc-send (stub :osc-send)]
                (show @lpad)
                (should-have-invoked :osc-send
                                     {:with [cli "lpad/show" (Integer. 1)]}))))

(describe "update-led"
          (with-stubs)
          (with lpad (OscLaunchpadOutput. cli))
          (it "should send OSC - path: lpad/led, message: <row> <col> <red> <green>"
              (with-redefs [tone/osc-send (stub :osc-send)]
                (update-led @lpad [5 6] [2 0])
                (should-have-invoked :osc-send
                                     {:with [cli "lpad/led"
                                             (Integer. 5) (Integer. 6) (Integer. 2) (Integer. 0)]}))))

(describe "brighness"
          (with-stubs)
          (with lpad (OscLaunchpadOutput. cli))
          (it "should send OSC - path: lpad/brightness, message: <brightness value>"
              (with-redefs [tone/osc-send (stub :osc-send)]
                (brightness @lpad 23)
                (should-have-invoked :osc-send
                                     {:with [cli "lpad/brightness" (Integer. 23)]}))))

(describe "clear-all"
          (with-stubs)
          (with lpad (OscLaunchpadOutput. cli))
          (it "should send a blank led OSC message for each sprite position"
              (with-redefs [tone/osc-send (stub :osc-send)]
                (clear-all @lpad)
                (doseq [[row col] (positions)]
                  (should-have-invoked :osc-send
                                       {:with [cli "lpad/led"
                                               (Integer. row) (Integer. col)
                                               (Integer. 0) (Integer. 0)] :times 1})))))