(ns ant.t-ant
  (:use ant.ant)
  (:use [midje.sweet]))


(fact "can create world"
      (:ant (make-world)) => {:x 0 :y 0}
      (:direction (make-world)) => :up
      (:path (make-world)) => #{})

(fact "can turn right"
      (turn-right :up) => :right
      (turn-right :right) => :down
      (turn-right :down) => :left
      (turn-right :left) => :up)

(fact "can turn left"
      (turn-left :up) => :left
      (turn-left :left) => :down
      (turn-left :down) => :right
      (turn-left :right) => :up)

(fact "can identify square color"
      (is-black? (make-world) {:x 0 :y 0}) => false
      (is-white? (make-world) {:x 0 :y 0}) => true)

(fact "ant turns right if on white"
      (:direction (update-world (make-world))) => :right)

(fact "ant toggles square when moving"
      (is-black? (update-world (make-world)) {:x 0 :y 0}) => true)

(fact "ant moves forward on update"
      (:ant (update-world (make-world))) => {:x 1 :y 0})

