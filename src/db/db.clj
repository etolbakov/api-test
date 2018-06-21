(ns db.db)

(defn uuid []
  (str (java.util.UUID/randomUUID)))

;; ------ initial data ------
(def steve
  {:id (uuid) :name "Steve" :age 24 :salary 7884 :company "Acme"})
(def bill
  {:id (uuid) :name "Bill" :age 28 :salary 8884 :company "Winn"})
(def jeff
  {:id (uuid) :name "Jeff" :age 31 :salary 9954 :company "Amma"})

(def id-to-employee
  (atom {(:id steve) steve
         (:id bill) bill
         (:id jeff) jeff}))

(defn get-all-employees []
  (vals @id-to-employee))

(defn get-employee-by-id [id]
  (get @id-to-employee id))

(defn create-employee [body]
  (let [person (-> (:body body)
                   (assoc :id (uuid)))]
    (swap! id-to-employee conj {(:id person) person})))

(defn delete-employee-by-id [id]
  (swap! id-to-employee dissoc id))