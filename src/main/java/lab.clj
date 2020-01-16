(ns lab
  (:require [clojure.core.async :as a]))
(def queue (a/chan 1 ))

(defn f2 []
  (a/take! queue
           (fn [x] (if (not= (type x) java.lang.String)
                     (do
                       (a/put! queue (str x))
                       (f2))))))
(defn f1 []
  (with-open [r (clojure.java.io/input-stream "C:\\\\myfile.txt")]
    (loop [c (.read r)]
      (if (not= c -1)
        (do
          (a/put! queue (char c) )
          (recur (.read r)))
        ( f2 )))) )

(f1)