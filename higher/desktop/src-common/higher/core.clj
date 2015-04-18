(ns higher.core
  (:require [play-clj.core :refer :all]
            [play-clj.g2d :refer :all]))

(defscreen main-screen
  :on-show
  (fn [screen entities]
    (update! screen :renderer (stage))
    (assoc (texture "Clojure_logo.gif") :x 50 :y 100 :vx 0 :vy 0))

  :on-render
  (fn [screen entities]
    (clear!)
    (render! screen entities))

  :on-key-down
  (fn [screen entities]
    (cond
      (= (:key screen) (key-code :dpad-up))
      (println "up")
      (= (:key screen) (key-code :dpad-down))
      (println "down")
      (= (:key screen) (key-code :dpad-left))
      (println "left")
      (= (:key screen) (key-code :dpad-right))
      (println "right"))))

(defgame higher-game
  :on-create
  (fn [this]
    (set-screen! this main-screen)))
