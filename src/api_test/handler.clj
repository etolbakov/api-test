(ns api-test.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.json :as middleware]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

;; ------ initial data ------
(defn uuid []
  (str (java.util.UUID/randomUUID)))

(def steve 
  {:id (uuid) :name "Steve" :age 24 :salary 7886 :company "Acme"})
(def bill 
  {:id (uuid) :name "Bill" :age 28 :salary 8885 :company "Winn"})
(def jeff 
  {:id (uuid) :name "Jeff" :age 31 :salary 9955 :company "Amma"})

(def id-to-employee
  (atom {(:id steve) steve
         (:id bill) bill
         (:id jeff) jeff}))

(defroutes app-routes
  (GET "/" [] "Hello World!")
  (GET "/employees" [] (response (vals @id-to-employee)))
  (GET "/employees/:id" [id] (response (get @id-to-employee id)))
  (POST "/employees" request
    (let [person (-> (:body request)
                     (assoc :id (uuid)))]
      (swap! id-to-employee conj {(:id person) person})
      {:status 200}))
  (DELETE "/employees/:id" [id]
    (swap! id-to-employee dissoc id))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
      (middleware/wrap-json-body {:keywords? true})
      middleware/wrap-json-response))