(ns myApp.db
  (:require [monger.core :as mg]))

(defn init-db [name]
  (mg/connect!)
  (mg/set-db! (mg/get-db name)))
