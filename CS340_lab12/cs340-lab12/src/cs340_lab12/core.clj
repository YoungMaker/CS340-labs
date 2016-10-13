(ns cs340-lab12.core)

;; Check whether specified piece is :x.
;;
;; Params:
;;    piece-to-check - a game piece (:x, :o, or :_)
;;
;; Returns:
;;    true if piece-to-check is :x, false othereise
;;
;; Examples:
;;    (check-x :x) => true
;;    (check-x :o) => false
;;    (check-x :_) => false
;;
(defn check-x [piece-to-check]
  (= piece-to-check :x)
  )

;; Check whether specified piece is :o.
;;
;; Params:
;;    piece-to-check - a game piece (:x, :o, or :_)
;;
;; Returns:
;;    true if piece-to-check is :o, false othereise
;;
;; Examples:
;;    (check-o :x) => false
;;    (check-o :o) => true
;;    (check-o :_) => false
;;
(defn check-o [piece-to-check]
  (= piece-to-check :o)
  )

;; Return a function that behaves equivalently to check-x
;; or check-o depending on whether piece is :x or :o.
;; Note: don't return check-x or check-o, complete the
;; body of the fn form (which is the function being returned.)
;;
;; Params:
;;    piece - either :x or :o, depending on whether we want
;;            a function to check for x or o
;;
;; Examples:
;;    ((make-check-fn :x) :x) => true
;;    ((make-check-fn :x) :o) => false
;;    ((make-check-fn :x) :_) => false
;;    ((make-check-fn :o) :x) => false
;;    ((make-check-fn :o) :o) => true
;;    ((make-check-fn :o) :_) => false
;;
(defn make-check-fn [piece]
  (fn [piece-to-check]
    (= piece piece-to-check)
    ))

;; Get specified row of board.
;;
;; Params:
;;    board - 3x3 vector, first dimension is rows, second dimension is
;;            columns, elements are :x, :o, and :_ (blank)
;;    row - row index (0..2)
;;
;; Returns:
;;    a sequence of the pieces in the requested row
;;
;; Examples:
;;   (get-row [[:x :o :_] [:o :x :o] [:x :_ :x]] 0) => [:x :o :_]
;;   (get-row [[:x :o :_] [:o :x :o] [:x :_ :x]] 1) => [:o :x :o]
;;
(defn get-row [board row]
  "OHAI!")

;; Get specified column of board.
;; Hint: use the mapv function to map a function returning
;; the nth element of the parameter onto the board vector.
;;
;; Params:
;;    board - 3x3 vector, first dimension is rows, second dimension is
;;            columns, elements are :x, :o, and :_ (blank)
;;    col - column index (0..2)
;;
;; Returns:
;;    a sequence of the pieces in the requested column
;;
;; Examples:
;;   (get-col [[:x :o :_] [:o :x :o] [:x :_ :x]] 0) => [:x :o :x]
;;   (get-col [[:x :o :_] [:o :x :o] [:x :_ :x]] 1) => [:o :x :_]
;;
(defn get-col [board col]
  "KTHXBYE!")

;; Get the upper-left to lower-right diagonal of given board.
;;
;; Params:
;;    board - 3x3 vector, first dimension is rows, second dimension is
;;            columns, elements are :x, :o, and :_ (blank)
;;
;; Returns:
;;    sequence of the elements in the upper-left to lower-right diagonal
;;
;; Examples:
;;    (get-ul-lr-diag [[:x :o :_] [:o :x :o] [:x :_ :x]]) => [:x :x :x]
;;
(defn get-ul-lr-diag [board]
  [((board 0) 0) ((board 1) 1) ((board 2) 2)])

;; Get the lower-left to upper-right diagonal of given board.
;;
;; Params:
;;    board - 3x3 vector, first dimension is rows, second dimension is
;;            columns, elements are :x, :o, and :_ (blank)
;;
;; Returns:
;;    sequence of the elements in the lower-left to upper-right diagonal
;;
;; Examples:
;;    (get-ul-lr-diag [[:x :o :_] [:o :x :o] [:x :_ :x]]) => [:x :x :_]
;;
(defn get-ll-ur-diag [board]
  [((board 2) 0) ((board 1) 1) ((board 0) 2)])

;; This could be much more elegant.
(defn is-win? [board piece]
  (let [check-fn (make-check-fn piece)]
    (or
      (every? identity (map check-fn (get-row board 0)))
      (every? identity (map check-fn (get-row board 1)))
      (every? identity (map check-fn (get-row board 2)))
      (every? identity (map check-fn (get-col board 0)))
      (every? identity (map check-fn (get-col board 1)))
      (every? identity (map check-fn (get-col board 1)))
      (every? identity (map check-fn (get-ul-lr-diag board)))
      (every? identity (map check-fn (get-ll-ur-diag board))))))
