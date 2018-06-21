(ns api.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [service.employee-service :as es]
            [compojure.route :as route]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.json :as middleware]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] "Hello World!")
  (GET "/employees" []
    (response (es/get-all-employees)))
  (GET "/employees/:id" [id]
    (response (es/get-employee-by-id id)))
  (POST "/employees" request
    (es/create-employee request))
  (DELETE "/employees/:id" [id]
    (es/delete-employee-by-id id))

  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
      (middleware/wrap-json-body {:keywords? true})
      middleware/wrap-json-response))