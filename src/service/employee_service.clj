(ns service.employee-service
  (:require [db.db :as db]))

(defn get-all-employees []
  (db/get-all-employees))

(defn get-employee-by-id [id]
  (db/get-employee-by-id id))

(defn create-employee [body]
  (db/create-employee body))

(defn delete-employee-by-id [id]
  (db/delete-employee-by-id id))