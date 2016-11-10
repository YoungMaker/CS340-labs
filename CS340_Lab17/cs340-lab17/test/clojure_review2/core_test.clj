(ns clojure-review2.core-test
  (:require [clojure.test :refer :all]
            [clojure-review2.core :refer :all]))

(deftest create-applicator-test
  (testing "create-applicator"
    (is (= 5 ((create-applicator + 2) 3)))
    (is (= 6 ((create-applicator - 3) 9)))
    (is (= [:hey :hey :heythere] ((create-applicator conj :heythere) [:hey :hey])))))

(deftest apply-to-sequence-test
  (testing "apply-to-sequence"
    (is (= [2 3 4] (apply-to-sequence + 1 [1 2 3])))
    (is (= [4 8 12 16] (apply-to-sequence * 4 [1 2 3 4])))
    (is (= [[:eggs :bacon] [:avocado :bacon] [:scallops :bacon]]
           (apply-to-sequence conj :bacon [[:eggs] [:avocado] [:scallops]])))
    (is (= [] (apply-to-sequence + 1 [])))
    ))

(deftest apply-to-sequence-the-hard-way-test
  (testing "apply-to-sequence-the-hard-way"
    (is (= [2 3 4] (apply-to-sequence-the-hard-way + 1 [1 2 3])))
    (is (= [4 8 12 16] (apply-to-sequence-the-hard-way * 4 [1 2 3 4])))
    (is (= [[:eggs :bacon] [:avocado :bacon] [:scallops :bacon]]
           (apply-to-sequence-the-hard-way conj :bacon [[:eggs] [:avocado] [:scallops]])))
    (is (= [] (apply-to-sequence-the-hard-way + 1 [])))
    ))

;   (swapify [:a :b :c :d]) => [:b :a :d :c]
;   (swapify ["n" "u" "x" "i"]) => ["u" "n" "i" "x"]
;   (swapify []) => []
;   (swapify [:x :y :z]) => [:y :x :z]
;   (swapify [:a]) => [:a]
(deftest swapify-test
  (testing "swapify"
    (is (= [:b :a :d :c] (swapify [:a :b :c :d])))
    (is (= ["u" "n" "i" "x"] (swapify ["n" "u" "x" "i"])))
    (is (= [] (swapify [])))
    (is (= [:y :x :z] (swapify [:x :y :z])))
    (is (= [:a] (swapify [:a])))))