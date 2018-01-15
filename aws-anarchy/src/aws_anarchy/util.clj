(ns aws-anarchy.util
  (:require [clojure.pprint :as pprint]
            [clojure.string :as string])
  (:import [com.amazonaws.services.kms AWSKMS AWSKMSClientBuilder]
           [com.amazonaws.services.kms.model DecryptRequest]
           [java.util Base64]
           (java.nio ByteBuffer)
           (java.nio.charset Charset)))

; -- dev -----------------------------------------------
(defn print-it [coll]
  (pprint/pprint coll)
  coll)

; -- utility -------------------------------------------
(defn exponential-backoff [time rate max f]
  (if (>= time max)
    (f)
    (try
      (f)
      (catch java.sql.SQLException e
        (Thread/sleep (* time 1000))
        (exponential-backoff (* time rate) rate max f)))))

; -- aws -----------------------------------------------
(defn decrypt [ciphertext]
  (let [decoder (Base64/getDecoder)
        decoded-text (.decode decoder ciphertext)
        kms-client (AWSKMSClientBuilder/defaultClient)
        decode-request (doto (DecryptRequest.)
                         (.withCiphertextBlob (ByteBuffer/wrap decoded-text)))
        decode-response (.decrypt kms-client decode-request)]
    (.toString (.decode (Charset/forName "UTF-8") (.getPlaintext decode-response)))))
