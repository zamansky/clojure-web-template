(ns  core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults site-defaults]]
            [ring.middleware.reload :refer [wrap-reload]]
            [hiccup.core :as h]
            [hiccup.page :as hp]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            ))


(defn -main []
  (println "HELLO"))


(defn index [req]
  (hp/html5
   [:head    (hp/include-css "/css/main.css") ]
   [:body
    [:div
     [:h1.bg-blue-400.text-4xl "hello World!!"]
     ]]))



(defroutes base-app
  (GET "/" [] index)
  (GET "/z" [] "<h1>ZZZZ</h1>")
  (route/not-found "<h1>Page not found</h1>"))

(def app
  (-> base-app
      (wrap-resource "public")
      (wrap-content-type)
      wrap-reload
      ))


(def server (jetty/run-jetty (wrap-reload #'app) {:port 8080 :join? false}))
(.start server)

;; (.stop server) and (.start server)

