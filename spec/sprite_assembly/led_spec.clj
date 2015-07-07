 (ns sprite-assembly.led-spec
  (:require [speclj.core :refer :all]
            [sprite-assembly.led :refer :all]))

(defn should-be-a-led
  [led]
  (should (>= (red led) 0))
  (should (>= (green led) 0))
  (should (<= (red led) r-depth))
  (should (<= (green led) g-depth)))

(describe "palette"
          (with colors (palette))

          (it "returns a collection of r-depth * g-depth colors"
              (should= (* r-depth g-depth) (count @colors)))

          (it "returns only valid leds"
              (doseq [color @colors]
                (should-be-a-led color))))

(describe "random"
          (context "no r-hold or g-hold"
                   (it "returns a led"
                       (should-be-a-led (random))))

          (context "r-hold, no g-hold"
                   (it "returns a led with red == r-hold"
                       (let [r (random :r-hold 3)]
                         (should-be-a-led r)
                         (should= 3 (red r)))))

          (context "g-hold, no r-hold"
                   (it "returns a led with green == g-hold"
                       (let [r (random :g-hold 2)]
                         (should-be-a-led r)
                         (should= 2 (green r))))))