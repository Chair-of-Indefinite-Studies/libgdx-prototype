(ns higher.core
  (:require [play-clj.core :refer :all]
            [play-clj.g2d :refer :all]))

(defn accelerate-horizontally [entity direction]
  "change entity vx depending on direction"
  (case direction
    :left (assoc entity :vx (dec (:vx entity)))
    :right (assoc entity :vx (inc (:vx entity)))))

(defn move [entity]
  "Move entity according to current velocity"
  (assoc entity :x (+ (:x entity) (:vx entity)) :y (+ (:y entity) (:vy entity))))

(defn fall [entity]
  "change vy according to the law of gravity"
  (cond
    (> (:y entity) 0) (assoc entity :vy (- (:vy entity) 1))
    :else entity))

(defn bound [entity]
  "reflects entity when hitting one of the game boundaries"
  (let [should-reflect-horizontally? (or (< (:x entity) 0) (> (:x entity) (game :width)))
        reflect-horizontally #(assoc % :vx (- (:vx %)))
        bound-horizontally #(if should-reflect-horizontally? (reflect-horizontally %) %)
        should-reflect-vertically? (or
                                    (and (< (:y entity) 0) (< (:vy entity) 0))
                                    (and (> (:y entity) (game :height)) (> (:vy entity) 0)))
        reflect-vertically #(assoc % :vy (- (:vy %)))
        bound-vertically #(if should-reflect-vertically? (reflect-vertically %) %)]
    (-> entity bound-horizontally bound-vertically)))

(defscreen main-screen
  :on-show
  (fn [screen entities]
    (update! screen :renderer (stage))
    (assoc (texture "Clojure_logo.gif") :x 50 :y 300 :vx 0 :vy 0))

  :on-render
  (fn [screen entities]
    (clear!)
    (render! screen entities)
    (map #(-> % fall move bound) entities))

  :on-key-down
  (fn [screen entities]
    (cond
      (= (:key screen) (key-code :dpad-up))
      (println "up")
      (= (:key screen) (key-code :dpad-down))
      (println "down")
      (= (:key screen) (key-code :dpad-left))
      (map #(accelerate-horizontally % :left) entities)
      (= (:key screen) (key-code :dpad-right))
      (map #(accelerate-horizontally % :right) entities))))

(defgame higher-game
  :on-create
  (fn [this]
    (set-screen! this main-screen)))
