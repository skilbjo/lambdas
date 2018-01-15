(defproject aws-anarchy "0.1.0"
  :uberjar-name "aws-anarchy.jar"
  :repositories {"atlassian" {:url "https://maven.atlassian.com/content/repositories/atlassian-3rdparty/"}}
  :dependencies [[com.amazonaws.athena.jdbc/AthenaJDBC41 "1.0.1-atlassian-hosted"]
                 [com.amazonaws/aws-java-sdk-kms "1.11.98"]
                 [com.amazonaws/aws-lambda-java-core "1.0.0"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/java.jdbc "0.7.3"]
                 [environ "1.1.0"]]
  :profiles {:dev {:plugins [[lein-cljfmt "0.5.6"]]}
             :uberjar {:aot :all}}
  :target-path "target/%s"
  :jvm-opts ["-Duser.timezone=PST8PDT"
             ; Same JVM options as deploy/bin/run-job uses in production
             "-Xms256m"
             "-Xmx2g"
             "-XX:MaxMetaspaceSize=128m"
             ; https://clojure.org/reference/compilation
             "-Dclojure.compiler.direct-linking=true"
             ; https://stackoverflow.com/questions/4659151/recurring-exception-without-a-stack-trace-how-to-reset
             "-XX:-OmitStackTraceInFastThrow"])
