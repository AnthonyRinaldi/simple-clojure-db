(ns myApp.web
  (:require [immutant.web :as web]
            [immutant.web.middleware :as immutant]
            [myApp.post :as post]
            [cheshire.core :as json]
            [compojure.route :as route]
            [compojure.core :refer (ANY GET POST defroutes)]
            [ring.util.response :refer (response redirect content-type)]
            [clojure.pprint :refer (pprint)]
            [environ.core :refer (env)])
  (:import [org.bson.types ObjectId])
  (:gen-class))

(defn echo
  "Echos the request back as a string."
  [request]
  (-> (response (with-out-str (pprint request)))
    (content-type "text/plain")))

;;Create a post.
(defn create [params]
  (let [id (post/create params)]
    {:status 201
     :headers {"Content-Type" "application/json"}
     :body (json/generate-string (assoc params :_id (str id)))}))

;;Fetch record from db.
(defn fetch [id]
  (let [object-id (ObjectId. id)
        post (post/fetch object-id)]
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/generate-string (assoc post :_id (str id)))}))

;;url endpoints...
(defroutes routes
  (POST "/posts" {params :params} (create params))
  (GET "/posts/:id" [id] (fetch id))
  ;;Prints any request to body.
  (ANY "*" [] echo))

(defn -main [& {:as args}]
  (web/run
    (-> routes
      (immutant/wrap-session {:timeout 20}))
;;  (db/init-db "name of your mongoDB")
    (merge {"host" (env :myApp-web-host), "port" (env :myApp-web-port)}
      args)))
