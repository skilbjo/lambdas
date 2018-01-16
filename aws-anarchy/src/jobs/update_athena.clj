(ns jobs.update-athena
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io]
            [aws-anarchy.sql :as sql]
            [aws-anarchy.util :as util])
  (:gen-class
   :name "jobs.update-athena"
   :implements [com.amazonaws.services.lambda.runtime.RequestStreamHandler]))

(defn- s3->m [event]
  (let [object-key  (-> event :Records first :s3 :object :key)
        [_ table]   (re-find #"[^\/]+\/[^\/]+\/([^\/]+)\/.*"
                             object-key)
        bucket      (-> event :Records first :s3 :bucket :name)
        fullpath    (str "s3://"
                         bucket
                         "/"
                         object-key)]
    {:bucket     bucket
     :object-key object-key
     :table      table
     :fullpath   fullpath}))

(defn main [event]
  (let [_       (println "Starting to update athena ... ")
        table   (-> event
                    s3->m
                    :table)
        _       (println "Running msck repair table on " table)
        results (util/exponential-backoff 1 2 180
                                          #(sql/query-athena "msck repair table :schema.:table;"
                                                  {:schema "dw"
                                                   :table table}))
        _              (println "Finished!")]))

(defn -handleRequest [_ event _ context]
  (let [event' (-> event
                   io/reader
                   (json/read :key-fn keyword))]
    (main event')))
