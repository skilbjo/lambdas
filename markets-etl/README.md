
```clojure
(defn- decrypt [ciphertext]
  (let [decoder (Base64/getDecoder)
        decoded-text (.decode decoder ciphertext)
        kms-client (AWSKMSClientBuilder/defaultClient)
        decode-request (doto (DecryptRequest.)
                         (.withCiphertextBlob (ByteBuffer/wrap decoded-text)))
        decode-response (.decrypt kms-client decode-request)]
    (.toString (.decode (Charset/forName "UTF-8") (.getPlaintext decode-response)))))
```
