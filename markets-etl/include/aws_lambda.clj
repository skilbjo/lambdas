(ns jobs.aws-lambda
  (:require [clj-time.core :as time]
            [clj-time.format :as format]
            [clojure.data.json :as json]
            [clojure.java.io :as io]
            [clojure.tools.cli :as cli]
            [clojure.tools.logging :as log]
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
  (let [{:keys [options summary errors]} (cli/parse-opts args cli-options)]
    (log/info "Starting jobs... ")
    ;(log/info "Currency... ")
    ;(currency/-main '-d (-> options :date))

    ;(log/info "Economics... ")
    ;(economics/-main '-d (-> options :date))

    ;(log/info "Equities... ")
    ;(equities/-main '-d (-> options :date))

    ;(log/info "Interest rates... ")
    ;(interest-rates/-main '-d (-> options :date))

    ;(log/info "Real estate... ")
    ;(real-estate/-main '-d (-> options :date))

    (log/info "Equities... ")
    ;(equities/-main "-d" "2018-01-02")
    (equities/-main "-d" (-> options :date))
    (log/info "Finished!")))

(defn main [& args]
  (let [_              (log/info "Starting jobs... ")
        _              (log/info "Currency... ")
        currency       (currency/-main)
        _              (log/info "Economics... ")
        economics      (economics/-main)
        _              (log/info "Equities... ")
        equities       (equities/-main)
        _              (log/info "Interest rates... ")
        interest-rates (interest-rates/-main)
        _              (log/info "Real estate... ")
        real-estate    (real-estate/-main)
        _              (log/info "Finished!")]

    (log/info "Notifying healthchecks.io ... ")
    (util/notify-healthchecks-io (-> :healthchecks-io-api-key
                                     env
                                     util/decrypt))))

(defn -handleRequest [_ event _ context]
  (let [event' (-> event
                   io/reader
                   (json/read :key-fn keyword))]
    (main)))
