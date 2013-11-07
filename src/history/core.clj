(ns history.core
  (:use [cascalog.api]
        [cascalog.more-taps :only (hfs-delimited)]
        [clj-time.core :only (day-of-week hour)]
        [clj-time.coerce])
  (:require [cascalog.ops :as c])
  (:gen-class))


(defmapop time-split
  "split timestamp into dow and hr"
  [timestamp]
  (let [ts (from-long (long (/ (Long/parseLong timestamp) 1000)))]        
    [(day-of-week ts) (hour ts)]))

(deffilterop is-interesting-url? [url]
  (. (. url toLowerCase) contains "clojure"))

(defn -main [& args]
  ; first query that just counts visits by hour by day of week
  (?<- (hfs-delimited "output/history")
       [?dow ?hr ?count]
       ((hfs-delimited "data/moz_historyvisits.csv" :delimiter "," :skip-header? true) _ _ _ ?visit-date _ _)
       (time-split ?visit-date :> ?dow ?hr)
       (c/count ?count))
  ; second query that merges two sources and counts visits for filtered url
  (?<- (hfs-delimited "output/filtered")
       [?dow ?hr ?count]
       ((hfs-delimited "data/moz_places_filtered.txt" :skip-header? true) 
         ?place-id ?url _ _ _ _ _ _ _ _ _)
       (is-interesting-url? ?url)
       ((hfs-delimited "data/moz_historyvisits.csv" :delimiter "," :skip-header? true) _ _ ?place-id ?visit-date _ _)
       (time-split ?visit-date :> ?dow ?hr)
       (c/count ?count)))
