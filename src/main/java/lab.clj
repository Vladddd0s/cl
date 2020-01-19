(ns lab
  (:require [clojure.core.async :as a]))
(def queue (a/chan 1 ))
(def queue1 (a/chan 1 ))
(defn f2 [s]
  (a/take! queue
           (fn [x]
             ( if (not= x -1)
               ( if (= (char 13) x)
                 (do
                   (println s)
                   ( a/put! queue1 s)
                   (f2 ""))
                 ( do
                   (f2 (str s x)) ) )
               ( do
                 (println s)
                 (a/put! queue1 s))))))
(defn f1 []
  (with-open [r (clojure.java.io/input-stream "C:\\\\myfile.txt")]
    (loop [c (.read r)]
      (if (not= c -1)
        (do
          (a/put! queue (char c))

          (recur (.read r)))
       (a/put! queue -1) ))) )

(f1)
(f2 "")