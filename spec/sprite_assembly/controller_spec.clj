(ns sprite-assembly.controller-spec
  (:require [speclj.core :refer :all]
            [sprite-assembly.controller :refer :all]))

(describe "build"
          (with controller (build))

          (it "builds a controller with a global mode"
              (should (satisfies? ControlMode (@controller :global))))

          (it "builds draw, palette, frames, and assembly modes"
              (doseq [mode [:draw :palette :frames :assembly]]
                (should (contains? @controller :modes))
                (should (satisfies? ControlMode
                                    (get (@controller :modes) mode))))))
