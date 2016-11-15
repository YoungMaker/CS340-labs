(ns cs340-exam2.core-test
  (:require [clojure.test :refer :all]
            [cs340-exam2.core :refer :all]))

(deftest rotate-triple-test
  (testing "rotate-triple"
    (is (= [:c :a :b] (rotate-triple [:a :b :c])))
    (is (= [3 1 2] (rotate-triple [1 2 3])))
    (is (= [:spam :spam "baked beans"] (rotate-triple [:spam "baked beans" :spam])))
    (is (= ["yip" "hup" "yip"] (rotate-triple ["hup" "yip" "yip"])))
    ))

(deftest make-repeated-applicator-test
 (testing "make-repeated-applicator"
    (is (= [[[:a :b :c]]] ((make-repeated-applicator first 0) [[[:a :b :c]]])))
    (is (= [[:a :b :c]] ((make-repeated-applicator first 1) [[[:a :b :c]]])))
    (is (= [:a :b :c] ((make-repeated-applicator first 2) [[[:a :b :c]]])))
    (is (= :a ((make-repeated-applicator first 3) [[[:a :b :c]]])))
    (is (= [:b :c :a] ((make-repeated-applicator rotate-triple 2) [:a :b :c])))
    ))

(deftest make-triple-rotator-test
  (testing "make-triple-rotator"
    (is (= [:a :b :c] ((make-triple-rotator 0) [:a :b :c])))
    (is (= [:c :a :b] ((make-triple-rotator 1) [:a :b :c])))
    (is (= [:b :c :a] ((make-triple-rotator 2) [:a :b :c])))
    (is (= [:a :b :c] ((make-triple-rotator 3) [:a :b :c])))
    (is (= ["baked beans" :spam :spam] ((make-triple-rotator 2) [:spam "baked beans" :spam])))
    ))

(deftest make-pairs-test
  (testing "make-pairs"
    (is (= [[:a :a] [:b :b] [:c :c]] (make-pairs [:a :b :c])))
    (is (= [[:spam :spam] [:spam :spam] ["baked beans" "baked beans"] [:spam :spam]]
           (make-pairs [:spam :spam "baked beans" :spam])))
    (is (= [[[:a] [:a]] [[:b] [:b]] [[:c] [:c]]] (make-pairs [[:a] [:b] [:c]])))
    ))

(deftest count-nested-test
  (testing "count-nested"
    (is (= 1 (count-nested :a)))
    (is (= 1 (count-nested 123)))
    (is (= 1 (count-nested "baked beans")))
    (is (= 3 (count-nested [:a :b :c])))
    (is (= 3 (count-nested [:a [[[:b [[[[:c]]]]]]]])))
    (is (= 9 (count-nested [:spam [[:spam]] [:spam :spam [[:spam] [[["baked beans"] :spam] :spam] :spam]]])))
    ))

(deftest my-eq-test
  (testing "my-eq"
    (is (my-eq 2 2))
    (is (not  (my-eq 2 3)))
    (is (my-eq :a :a))
    (is (not (my-eq :a :b)))
    (is (my-eq [:a :b :c] [:a :b :c]))
    (is (my-eq [:a [:b :c]] [:a [:b :c]]))
    (is (not (my-eq [:a [:b :c]] [[:a :b] :c])))
    (is (not (my-eq 123 [123])))
    ))
