(ns ant.world
  (:import (java.awt Color Dimension)
    (javax.swing JPanel JFrame Timer JOptionPane)
    (java.awt.event ActionListener KeyListener))
  (:use clojure.contrib.import-static))

(import-static java.awt.event.KeyEvent VK_LEFT VK_RIGHT VK_UP VK_DOWN)

(def center [500 500])

(defstruct controls :magnification :center :clear)

(defn draw-object [g obj controls]
  (let [
;    mag (:magnification controls)
;    sun-center (:center controls)
;    x-offset (- (:x center) (* mag (:x sun-center)))
;    y-offset (- (:y center) (* mag (:y sun-center)))
;    x (+ x-offset (* mag (:x (:position obj))))
;    y (+ y-offset (* mag (:y (:position obj))))
;    s (max 2 (* mag (size-by-mass obj)))
;    half-s (/ s 2)
;    c (color-by-mass obj)
    ]
;    (.setColor g c)
;    (.fillOval g (- x half-s) (- y half-s) s s)
    (.setColor g Color/red)
    (.fillOval g 100 100 500 500)
    )
  )

(defn draw-world [g world controls]
  (doseq [obj world]
    (draw-object g obj controls)
    )
  (.clearRect g 0 0 1000 20))

(defn update-world [world controls]
  (dosync
    world))
    ;(alter world #(object/update-all %))))

(defn magnify [factor controls world]
  (dosync
    (let [new-mag (* factor (:magnification @controls))]
      (alter controls #(assoc % :magnification new-mag))
      (alter controls #(assoc % :clear true)))))

(defn reset-screen-state [controls]
  (dosync 
    (alter controls #(assoc % :clear false))))

(defn toggle-trail [controls]
  (dosync 
    (alter controls #(assoc % :trails (not (:trails @controls))))))

(defn- quit-key? [c]
  (= \q c)
  )

(defn- plus-key? [c]
  (or (= \+ c) (= \= c))
  )

(defn- minus-key? [c]
  (or (= \- c) (= \_ c))
  )

(defn- space-key? [c]
  (= \space c)
  )

(defn- trail-key? [c]
  (= \t c)
  )

(defn handle-key [c world controls]
  (cond
    (quit-key? c) (System/exit 1)
    (plus-key? c) (magnify 1.1 controls world)
    (minus-key? c) (magnify 0.9 controls world)
    (space-key? c) (magnify 1.0 controls world)
    (trail-key? c) (toggle-trail controls)
    ))

(defn world-panel [frame world controls]
  (proxy [JPanel ActionListener KeyListener] []
    (paintComponent [g]
      (draw-world g @world @controls)
      (reset-screen-state controls))
    (actionPerformed [e]
      (update-world world @controls)
      (.repaint this))
    (keyPressed [e]
      (handle-key (.getKeyChar e) world controls)
      (.repaint this))
    (getPreferredSize []
      (Dimension. 1000 1000))
    (keyReleased [e])
    (keyTyped [e])))

(defn create-world []
  '(1))
  ;(ant.world/make))

(defn world-frame []
  (let [controls (ref (struct-map controls 
                                  :magnification 1.0
                                  :trails false
                                  :clear false))
        world (ref (create-world))
        frame (JFrame. "Ant")
        panel (world-panel frame world controls)
        timer (Timer. 1 panel)]
    (doto panel
      (.setFocusable true)
      (.addKeyListener panel))
    (doto frame
      (.add panel)
      (.pack)
      (.setVisible true)
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE))
    (.start timer)))

(defn -main [& m]
  (world-frame))
