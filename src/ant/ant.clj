(ns ant.ant)

(defn make-world []
  {:ant { :x 0 :y 0 }
   :direction :up
   :path '()})

(defn turn-right
  [dir]
  (cond
    (= :up dir) :right
    (= :right dir) :down
    (= :down dir) :left
    (= :left dir) :up))

(defn turn-left
  [dir]
  (cond
    (= :up dir) :left
    (= :left dir) :down
    (= :down dir) :right
    (= :right dir) :up))
