 (ns sprite-assembly.mode-control-spec
   (:require [speclj.core :refer :all]
             [sprite-assembly.mode-control :refer :all]))

(describe "change-mode"
          (it "changes the model's mode"
             (let [model { :active-mode :frames }
                   changed (change-mode :draw model)
                   changed-again (change-mode :palette changed)]
              (should= :draw (:active-mode changed))
              (should= :palette (:active-mode changed-again)))))

(describe "handler"
          (it "does nothing when a non-mode-changing button is pressed"
              (let [event [3 5 1]
                    model { :active-mode :assembly }
                    handled (handler event model)]
               (should= model handled)))

          (it "does nothing when a non-mode-changing button is released"
              (let [event [5 5 0]
                    model { :active-mode :draw }
                    handled (handler event model)]
               (should= model handled)))

          (it "changes mode when a mode-changing button is pressed"
              (let [event [0 5 1]
                    model { :active-mode :palette }
                    handled (handler event model)]
               (should-not= (:active-mode model) (:active-mode handled))
               (should= :frames (:active-mode handled))))

          (it "does nothing when a mode-changing button is relased"
               (let [event [0 4 0]
                     model { :active-mode :palette }
                     handled (handler event model)]
                (should= model handled))))