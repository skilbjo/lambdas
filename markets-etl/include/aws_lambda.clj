(ns jobs.aws-lambda
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io]
            [environ.core :refer [env]]
            [jobs.currency :as currency]
            [jobs.economics :as economics]
            [jobs.equities :as equities]
            [jobs.interest-rates :as interest-rates]
            [jobs.real-estate :as real-estate]
            [markets-etl.util :as util])
  (:gen-class
    :name "jobs.aws-lambda"
    :implements [com.amazonaws.services.lambda.runtime.RequestStreamHandler]))

(defn main []
  (let [_              (println "Starting jobs... ")

        _              (println "Currency... ")
        currency       (currency/-main)

        _              (println "Economics... ")
        econmics       (economics/-main)

        _              (println "Equities... ")
        equities       (equities/-main)

        _              (println "Interest rates... ")
        interest-rates (interest-rates/-main)

        _              (println "Real estate... ")
        real-estate    (real-estate/-main)

        _              (println "Finished!")]))

(defn -handleRequest [_ event _ context]
  (let [event' (-> event
                   io/reader
                   (json/read :key-fn keyword))]
    (main)))
