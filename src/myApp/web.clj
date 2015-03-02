(ns myApp.web
  (:require [immutant.web :as web]
            [immutant.web.middleware :as immutant]
            [myApp.post :as post]
;;            [myApp.db :as db]
            [cheshire.core :as json]
            [compojure.route :as route]
            [compojure.core :refer (ANY GET POST PUT defroutes)]
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

(def to-json json/generate-string)

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application.json"}
   :body (to-json data)})

;;Create a post.
(defn create [params]
  (let [id (post/create params)]
    (json-response  (assoc params :_id (str id)) 201)))

;;Fetch record from db.
(defn fetch [id]
  (let [object-id (ObjectId. id)
        post (post/fetch object-id)]
    (json-response (assoc post :_id (str id)) 200)))

;;url endpoints...
(defroutes routes
  (POST "/posts" {params :params} (create params))
  (GET "/posts/:id" [id] (fetch id))
  (GET "/" []
        (json-response "Welcome to myApp."))
  ;;Prints any request to body.
  (ANY "*" [] echo))

(defn -main [& {:as args}]
;; (db/init-db "dev")  
 (web/run
    (-> routes
      (immutant/wrap-session {:timeout 20}))   
    (merge {"host" (env :myApp-web-host), "port" (env :myApp-web-port)}
      args)))
