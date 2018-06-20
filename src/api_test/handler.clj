(ns api-test.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.json :as middleware]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

;; ------ initial data ------
(def steve {:id 1 :name "Steve" :age 24 :salary 7886 :company "Acme"})
(def bill  {:id 2 :name "Bill"  :age 28 :salary 8885 :company "Winn"})
(def jeff  {:id 3 :name "Jeff"  :age 31 :salary 9955 :company "Amma"})
(def employees (atom #{steve bill jeff}))
(def employeesMap {:1 steve :2 bill :3 jeff})

(defroutes app-routes
  (GET "/" [] "Hello World!")
  (GET "/employees-old" [] (response @employees))
  (GET "/employees" [] (response (vals employeesMap)))
  ;(GET ["/employees/:id", :id #"[0-9]+"] [id] (response (first (filter #(= (:id %) (Integer/parseInt id)) @employees))))
  (GET ["/employees/:id", :id #"[0-9]+"] [id] (response (get employeesMap
                                                             (keyword id)))))
  ;(GET "/employees/" [param val]
  ;  (response (if (.contains ["name", "company"] param)
  ;              (first (filter #(.equals (.toLowerCase ((keyword param) %)) val) @employees))
  ;              (first (filter #(.equals ((keyword param) %) (Long/parseLong val)) @employees)))))


           ;(POST "/employees" request
  ;  (let [person {:id      (inc (apply max (map #(:id %) @employees)))
  ;                :name    (get-in request [:body :name])
  ;                :age     (get-in request [:body :age])
  ;                :salary  (get-in request [:body :salary])
  ;                :company (get-in request [:body :company])}]
  ;    (swap! employees conj person)
  ;    {:status 200}))
(POST "/employees" request
  (let [person (-> (:body request)
                   (assoc :id (inc (apply max (map #(:id %) @employees)))))]
    (swap! employees conj person)
    {:status 200}))

  ;(DELETE ["/employees/:id", :id #"[0-9]+"] [id]
  ;  (let [person (first (filter #(= (:id %) (Integer/parseInt id)) @employees))]
  ;    (swap! employees disj person)
  ;    {:status 200}))
(DELETE ["/employees/:id", :id #"[0-9]+"] [id]
    (dissoc employeesMap (keyword id) {:status 200}))
(route/resources "/")
(route/not-found "Not Found")

(def app
  (-> (handler/site app-routes)
      (middleware/wrap-json-body {:keywords? true})
      middleware/wrap-json-response))
