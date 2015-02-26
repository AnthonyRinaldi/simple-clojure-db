(ns myApp.core
  (:require myApp.web)
  (:gen-class))

(defn -main [& args]
  (apply myApp.web/-main args))
