(ns sprite-assembly.sprite
  (require [sprite-assembly.led :as led]
           [clojure.math.combinatorics :as combo]))

(def nrows
  "Number of rows."
  8)

(def ncols
  "Number of cols."
  8)

(def positions
  "Possible sprite led positions."
  (combo/cartesian-product (range nrows) (range ncols)))

(defn fill
  "Create a sprite according to a fill function."
  [fill-fn]
  (reduce #(assoc %1 %2 (fill-fn %2)) {} positions))

(defn blank
  "Empty sprite."
  []
  (fill (fn [pos] (led/named :black))))