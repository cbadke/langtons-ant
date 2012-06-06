(ns ant.ant)

(defn make-world []
  {:ant { :x 0 :y 0 }
   :direction :up
   :path #{}})

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

(defn is-black?
  [world coord]
  (contains? (:path world) coord))

(defn is-white?
  [world coord]
  (not (is-black? world coord)))

(defn- toggle-ant-square
  [world]
  (let [coord (:ant world)]
    (if (is-black? world coord)
      (assoc world :path (disj (:path world) coord))
      (assoc world :path (conj (:path world) coord)))))

(defn- turn-ant
  [world]
  (let [ant-square (:ant world)
        ant-dir (:direction world)]
    (if
      (is-white? world ant-square)
      (assoc world :direction (turn-right ant-dir)) 
      (assoc world :direction (turn-left ant-dir)))))

(defn- move-ant-forward
  [world]
  (let [x (-> world :ant :x)
        y (-> world :ant :y)
        dir (:direction world)
        new-x (cond
                (= dir :right) (+ x 1)
                (= dir :left ) (- x 1)
                :else x)
        new-y (cond
                (= dir :down) (+ y 1)
                (= dir :up ) (- y 1)
                :else y)]
    (assoc world :ant {:x new-x :y new-y})))

(defn update-world
  [world]
  (move-ant-forward
    (toggle-ant-square
      (turn-ant world))))
