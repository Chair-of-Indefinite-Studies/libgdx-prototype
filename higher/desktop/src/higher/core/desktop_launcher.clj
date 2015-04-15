(ns higher.core.desktop-launcher
  (:require [higher.core :refer :all])
  (:import [com.badlogic.gdx.backends.lwjgl LwjglApplication]
           [org.lwjgl.input Keyboard])
  (:gen-class))

(defn -main
  []
  (LwjglApplication. higher-game "higher" 800 600)
  (Keyboard/enableRepeatEvents true))
