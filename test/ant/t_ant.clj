(ns ant.t-ant
  (:use ant.ant)
  (:use [midje.sweet]))


(fact "can create world"
      (:ant (make-world)) => {:x 0 :y 0}
      (:direction (make-world)) => :up
      (:path (make-world)) => '())

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
