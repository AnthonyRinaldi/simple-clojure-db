(ns myApp.web
  (:require [immutant.web             :as web]
            [immutant.web.async       :as async]
            [immutant.web.sse         :as sse]
            [immutant.web.middleware  :as immutant]
            [compojure.route          :as route]
            [compojure.core     :refer (ANY GET defroutes)]
            [ring.util.response :refer (response redirect content-type)]
            [clojure.pprint     :refer (pprint)]
            [environ.core       :refer (env)]))

(defn echo
  "Echos the request back as a string."
  [request]
  (-> (response (with-out-str (pprint request)))
    (content-type "text/plain")))

(defroutes routes
  (GET "/" {c :context} (redirect (str c "/index.html")))
  (route/resources "/")
  (ANY "*" [] echo))


(defn -main [& {:as args}]
  (web/run
    (-> routes
      (immutant/wrap-session {:timeout 20}))
    (merge {"host" (env :myApp-web-host), "port" (env :myApp-web-port)}
      args)))
