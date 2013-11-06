(defproject history "0.1.0-SNAPSHOT"
  :description "Project to experiment with Cascalog"
  :url "http://github.com/axhixh/history-cascalog"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [cascalog "1.10.0"]
                 [cascalog-more-taps "0.3.0"]
                 [clj-time "0.6.0"]]
  :uberjar-name "history.jar"
  :aot [history.core]
  :main history.core
  :profiles {:provided {:dependencies [[org.apache.hadoop/hadoop-core "1.2.1"]]}})
 
