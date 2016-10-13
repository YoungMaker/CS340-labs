(ns cs340-lab12.core-test
  (:require [clojure.test :refer :all]
            [cs340-lab12.core :refer :all]))

(deftest check-x-test
  (testing "check-x"
    (is (check-x :x))
    (is (not (check-x :o)))
    (is (not (check-x :_)))))

(deftest check-o-test
  (testing "check-o"
    (is (not (check-o :x)))
    (is (check-o :o))
    (is (not (check-o :_)))))

(deftest make-check-fn-test
  (testing "make-check-fn"
    (is ((make-check-fn :x) :x))
    (is (not ((make-check-fn :x) :o)))
    (is (not ((make-check-fn :x) :_)))
    (is (not ((make-check-fn :o) :x)))
    (is ((make-check-fn :o) :o))
    (is (not ((make-check-fn :o) :_)))
    ))

(def test-board
  [[:x :o :_]
   [:o :x :o]
   [:x :_ :x]])

(deftest get-row-test
  (testing "get-row"
    (is (= [:x :o :_] (get-row test-board 0)))
    (is (= [:o :x :o] (get-row test-board 1)))
    (is (= [:x :_ :x] (get-row test-board 2)))
    ))

(deftest get-col-test
  (testing "get-col"
    (is (= [:x :o :x] (get-col test-board 0)))
    (is (= [:o :x :_] (get-col test-board 1)))
    (is (= [:_ :o :x] (get-col test-board 2)))
    ))

(deftest get-ul-lr-diag-test
  (testing "get-ul-lr-diag"
    (is (= [:x :x :x] (get-ul-lr-diag test-board)))))

(deftest get-ll-ur-diag-test
  (testing "get-ul-lr-diag"
    (is (= [:x :x :_] (get-ll-ur-diag test-board)))))

(deftest is-win?-test
  (let [cx (make-check-fn :x)
        co (make-check-fn :o)]
    (testing "is-win?"
      (is (not (every? identity (map cx (get-row test-board 0)))))
      (is (not (every? identity (map cx (get-row test-board 1)))))
      (is (not (every? identity (map cx (get-row test-board 2)))))
      (is (not (every? identity (map co (get-row test-board 0)))))
      (is (not (every? identity (map co (get-row test-board 1)))))
      (is (not (every? identity (map co (get-row test-board 2)))))
      (is (not (every? identity (map cx (get-col test-board 0)))))
      (is (not (every? identity (map cx (get-col test-board 1)))))
      (is (not (every?  identity (map cx (get-col test-board 2)))))
      (is (not (every?  identity (map co (get-col test-board 0)))))
      (is (not (every?  identity (map co (get-col test-board 1)))))
      (is (not (every?  identity (map co (get-col test-board 2)))))
      (is (every?  identity (map cx (get-ul-lr-diag test-board)))) ; <-- winning configuration for X
      (is (not (every?  identity (map co (get-ul-lr-diag test-board)))))
      (is (not (every?  identity (map cx (get-ll-ur-diag test-board)))))
      (is (not (every?  identity (map co (get-ll-ur-diag test-board)))))
      (is (is-win? test-board :x))
      (is (not (is-win? test-board :o)))
      )))
