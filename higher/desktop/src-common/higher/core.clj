(ns higher.core
  (:require [play-clj.core :refer :all]
            [play-clj.g2d :refer :all]))

(defscreen main-screen
  :on-show
  (fn [screen entities]
    (update! screen :renderer (stage))
    (texture "Clojure_logo.gif"))

  :on-render
  (fn [screen entities]
    (clear!)
    (render! screen entities)))

(defgame higher-game
  :on-create
  (fn [this]
    (set-screen! this main-screen)))
