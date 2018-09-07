(ns jobs.aws-lambda
  (:require [clj-time.core :as time]
            [clj-time.format :as format]
            [clojure.data.json :as json]
            [clojure.java.io :as io]
            [environ.core :refer [env]]
            [jobs.currency :as currency]
            [jobs.economics :as economics]
            [jobs.equities :as equities]
            [jobs.interest-rates :as interest-rates]
            [jobs.real-estate :as real-estate]
            [markets-etl.util :as util])
  (:gen-class
    :name jobs.aws-lambda
    :implements [com.amazonaws.services.lambda.runtime.RequestStreamHandler]))

(def cli-options
  [["-d" "--date DATE" "Start date (month) (yyyy-mm-dd format) to start processing"
    :default  util/last-week]
   ["-h" "--help"]])

(defn -main [& args]
  (equities/-main '-d '"2018-01-01")
  (let [{:keys [options summary errors]} (cli/parse-opts args cli-options)
        _              (println "Starting jobs... ")

        _              (println "Currency... ")
        currency       (currency/-main '-d (-> options :date))

        _              (println "Economics... ")
        economics      (economics/-main'-d (-> options :date))

        _              (println "Equities... ")
        equities       (equities/-main'-d (-> options :date))

        _              (println "Interest rates... ")
        interest-rates (interest-rates/-main'-d (-> options :date))

        _              (println "Real estate... ")
        real-estate    (real-estate/-main'-d (-> options :date))

        _              (println "Finished!")]))

(defn main [& args]
  (let [_              (println "Starting jobs... ")

        _              (println "Currency... ")
        currency       (currency/-main)

        _              (println "Economics... ")
        economics      (economics/-main)

        _              (println "Equities... ")
        equities       (equities/-main)

        _              (println "Interest rates... ")
        interest-rates (interest-rates/-main)

        _              (println "Real estate... ")
        real-estate    (real-estate/-main)

        _              (println "Notifying healthchecks.io ... ")
        _              (util/notify-healthchecks-io (-> :healthchecks-io-api-key
                                                        env
                                                        util/decrypt))

        _              (println "Finished!")]))

(defn -handleRequest [_ event _ context]
  (let [event' (-> event
                   io/reader
                   (json/read :key-fn keyword))]
    (main)))
