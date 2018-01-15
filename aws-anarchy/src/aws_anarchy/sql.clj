(ns aws-anarchy.sql
  (:require [aws-anarchy.util :as util]
            [clojure.string :as string]
            [clojure.java.jdbc :as jdbc]
            [environ.core :refer [env]])
  (:import [java.sql DriverManager Connection]
           [java.util Properties]))

(defn- prepare-statement
  [sql params]
  (loop [sql sql
         kvs (map identity params)]
    (if (empty? kvs)
      sql
      (let [[[k v] & others] kvs]
        (recur (string/replace sql (str k) (str (jdbc/sql-value v)))
               others)))))

(defn get-athena-conn []
  (DriverManager/getConnection
   (-> :jdbc-athena-uri env util/decrypt)
   (doto (Properties.)
     (.put "user"           (-> :aws-access-key-id env util/decrypt))
     (.put "password"       (-> :aws-secret-access-key env util/decrypt))
     (.put "s3_staging_dir" (-> :s3-staging-dir env util/decrypt))
     #_(.put "log_path"       "/tmp/athenalog.out")
     #_(.put "log_level"      "DEBUG"))))

(defn query-athena
  ([sql]
   (query-athena sql {}))
  ([sql params]
   (with-open [conn (get-athena-conn)]
     (let [sql     (-> sql
                       (string/replace #";" "")
                       (string/replace #"--" "")
                       #_(string/replace #"\/" "") ; removes America/Los_Angeles
                       (string/replace #"\/\*" "")
                       (string/replace #"\*\\" "")
                       (prepare-statement params))
           results (-> conn
                       (.createStatement)
                       (.executeQuery sql))]
       (jdbc/metadata-result results)))))
