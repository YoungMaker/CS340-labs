(ns cs340-exam2.core)

; Problem 1 [25 points]
;
; This function takes a single argument, which will be a vector
; consisting of three values.  It should "rotate" the values
; so that the first and second values become the second and third
; values, and the last value becomes the first value.
;
; Hints:
; * Consider destructuring the vector.
;
; Examples:
;   (rotate-triple [:a :b :c]) => [:c :a :b]
;   (rotate-triple [1 2 3]) => [3 1 2]
;   (rotate-triple [:spam "baked beans" :spam]) => [:spam :spam "baked beans"]
;
(defn rotate-triple [triple]
  (let [[a b c] triple]
    [c a b]))


; Problem 2 [25 points]
;
; This function takes a function f and a non-negative
; integer count n and returns a function that applies f
; to its argument n times.  I.e., the first call applies
; f to the returned function's argument, the second call
; applies f to the result of the first call, etc.
;
;   ((make-repeated-applicator first 0) [[[:a :b :c]]]) => [[[:a :b :c]]]
;   ((make-repeated-applicator first 1) [[[:a :b :c]]]) => [[:a :b :c]]
;   ((make-repeated-applicator first 2) [[[:a :b :c]]]) => [:a :b :c]
;   ((make-repeated-applicator first 3) [[[:a :b :c]]]) => :a
;   ((make-repeated-applicator rotate-triple 2) [:a :b :c]) => [:b :c :a]
;
; Hints:
; * Return a function taking a single parameter
; * The body of the returned function should use loop/recur
; * Use two loop variables remaining and acc: remaining is
;   the remaining number of times to apply the function f
;   (initially n), and acc is the accumulator (initially
;   the parameter of the inner function
; * The recursion ends when remaining reaches 0
; * Update the accumulator by applying f to the current accumulator
;   value
;
(defn make-repeated-applicator [f n]
  (fn [x] 
    (loop [remaning n
           acc x]
      (if (= remaning 0)
        acc
        (recur (- remaning 1) (f acc))))))


; Problem 3 [10 points]
;
; This function takes a non-negative integer and returns a function
; that applies rotate-triple to its argument the specified number
; of times.
;
; Hints:
; * Return a function created with make-repeated-applicator
;   (specifying that rotate-triple should be applied n times)
; * This should be easy if rotate-triple and make-repeated-applicator
;   are working correctly
;
; Examples:
;
;   ((make-triple-rotator 0) [:a :b :c]) => [:a :b :c]
;   ((make-triple-rotator 1) [:a :b :c]) => [:c :a :b]
;   ((make-triple-rotator 2) [:a :b :c]) => [:b :c :a]
;   ((make-triple-rotator 3) [:a :b :c]) => [:a :b :c]
;   ((make-triple-rotator 2) [:spam "baked beans" :spam]) => ["baked beans" :spam :spam]
;
(defn make-triple-rotator [n]
  (make-repeated-applicator rotate-triple n))


; Problem 4 [15 points]
; This function takes a sequence and returns a sequence.
; Each member of the returned sequence should be a vector
; containing two copies of the corresponding member of
; the original sequence.
;
; Hints:
; - Your function must either be tail recursive, or must process
;   the sequence using a built-in function which is efficient
;   for sequences of arbitrary length
; - The map or mapv functions could be useful
;
; Examples:
;   (make-pairs [:a :b :c]) => [[:a :a] [:b :b] [:c :c]]
;   (make-pairs [:spam :spam "baked beans" :spam]) =>
;     [[:spam :spam] [:spam :spam] ["baked beans" "baked beans"] [:spam :spam]]
;   (make-pairs [[:a] [:b] [:c]]) => [[[:a] [:a]] [[:b] [:b]] [[:c] [:c]]]
;
(defn make-pairs [a-seq]
  (loop [remaining a-seq
         acc []]
    (if (empty? remaining)
      acc
    (recur (rest remaining) (conj acc [(first remaining) (first remaining)])))))


; Problem 5 [15 points]
;
; This function takes a single argument and returns a count.
; The count is defined as follows:
;
; - If the value is not a sequence, then the function should return 1
; - If the value is a sequence, then the function should return
;   the sum of applying the function recursively to each
;   member of the sequence
;
; In other words, members that aren't sequences
; should be counted directly, and members that *are* sequences
; should have their members counted recursively (using a recursive
; call to this function.)
;
; Hints/specifications:
; * Your function does not need to be tail recursive.
; * Use the sequential? predicate function to determine whether
;   or not a value is a sequence.
; * The not function inverts a boolean argument: for example,
;      (not (sequential? x))
;   would return true if x is not a sequence
; * The map and reduce functions could be useful
;
; Examples:
;   (count-nested :a) => 1
;   (count-nested 123) => 1
;   (count-nested "baked beans") => 1
;   (count-nested [:a :b :c]) => 3
;   (count-nested [:a [[[:b [[[[:c]]]]]]]]) => 3
;   (count-nested [:spam [[:spam]] [:spam :spam [[:spam] [[["baked beans"] :spam] :spam] :spam]]])
;     => 9
;
(defn count-nested [val]
  (if (not (sequential? val))
    1
  (loop [value val acc 0]
  (if (empty? value)
    acc
  (recur (rest value) (+ acc (count-nested (first value))))))))


; Problem 6 [10 points]
;
; Recursively compare two values for equality.
; The comparison should work as follows:
; * If neither the left nor right value is a sequence, then they
;   should be considered equal if and only if the built-in =
;   function considers them to be equal.
; * If both the left and right values are sequences, then they are equal
;   if and only if they contain the same number of elements
;   and each corresponding pair of elements is considered equal
;   as determined by a recursive call to my-eq (this function).
; * If one of the left or right values is a sequence, but not the
;   other, then they are not equal.
;
; Hints/specifications:
; * Use the sequential? predicate function to determine whether
;   or not a value is a sequence.
; * The not function inverts a boolean argument: for example,
;      (not (sequential? x))
;   would return true if x is not a sequence
; * The and special form evaluates as true if all of
;   its expressions are true, and evaluates to false otherwise:
;   for example, if x is 3, then
;      (and (> x 1) (> x 2)) => true
;      (and (> x 2) (> x 3)) => false
; * The or special form evaluates as true if any of
;   its expressions are true, and evaluates to false otherwise:
;   for example, if x is 3, then
;      (or (< x 4) (< x 0)) => true
;      (or (< x 3) (< x 0)) => false
; * It is not sufficient to just call = on the function's
;   parameters: your function needs to recursively process
;   the two values as described above.
;
; Your function does not need to be tail recursive.
;
; Examples:
;   (my-eq 2 2)  => true
;   (my-eq 2 3)  => false
;   (my-eq :a :a) => true
;   (my-eq :a :b) => false
;   (my-eq [:a :b :c] [:a :b :c]) => true
;   (my-eq [:a [:b :c]] [:a [:b :c]]) => true
;   (my-eq [:a [:b :c]] [[:a :b] :c]) => false
;   (my-eq 123 [123]) => false
;
(defn my-eq [left right]
  "KTHXBYE!")
