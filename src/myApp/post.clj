(ns myApp.post
  (:require [monger.collection :as mc])
  (:import [org.bson.types ObjectId]))

(defn fetch [id]
  (mc/find-one-as-map "posts" { :_id id }))

(defn create [post]
  (let [id (ObjectId.)]
    (mc/insert "posts" (assoc post :_id id))
    id))
