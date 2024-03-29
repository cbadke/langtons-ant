(ns ant.world
  (:import (java.awt Color Dimension)
    (javax.swing JPanel JFrame Timer JOptionPane)
    (java.awt.event ActionListener KeyListener))
  (:use clojure.contrib.import-static)
  (:use ant.ant))

(import-static java.awt.event.KeyEvent VK_LEFT VK_RIGHT VK_UP VK_DOWN)

(def screen {:x 500 :y 500})
(def center {:x 250  :y 250 })
(def ant-size 16)

(defstruct controls :magnification)

(defn draw-object 
  ([g obj controls]
   (draw-object g obj controls Color/black))
  ([g obj controls color]
   (let [mag (:magnification controls)
         width (max 2 (* mag ant-size))
         half-width (/ width 2)
         x (* width (:x obj))
         y (* width (:y obj))]
     (.setColor g color)
     (.fillRect g (+ (:x center) x) (+ (:y center) y) width width))))

(defn draw-ant
  [g ant controls]
  (draw-object g ant controls Color/red))

(defn draw-world [g world controls]
  (doseq [obj (:path world)]
    (draw-object g obj controls))
  (draw-ant g (:ant world) controls))

(defn update [world controls]
  (dosync
    (alter world #(update-world %))))

(defn magnify [factor controls world]
  (dosync
    (let [new-mag (* factor (:magnification @controls))]
      (alter controls #(assoc % :magnification new-mag)))))

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

(defn handle-key [c world controls]
  (cond
    (quit-key? c) (System/exit 1)
    (plus-key? c) (magnify 0.5 controls world)
    (minus-key? c) (magnify 2.0 controls world)
    (space-key? c) (magnify 1.0 controls world)
    ))

(defn world-panel [frame world controls]
  (proxy [JPanel ActionListener KeyListener] []
    (paintComponent [g]
      (proxy-super paintComponent g)
      (draw-world g @world @controls))
    (actionPerformed [e]
      (update world @controls)
      (.repaint this))
    (keyPressed [e]
      (handle-key (.getKeyChar e) world controls)
      (.repaint this))
    (getPreferredSize []
      (Dimension. (screen :x) (screen :y)))
    (keyReleased [e])
    (keyTyped [e])))

(defn create-world []
  (make-world))

(defn world-frame []
  (let [controls (ref (struct-map controls 
                                  :magnification 1.0
                                  :trails false))
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
