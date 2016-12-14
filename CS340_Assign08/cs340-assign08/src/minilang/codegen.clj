(ns minilang.codegen
  (:require [minilang.lexer :as lexer])
  (:require [minilang.node :as node])
  (:require [minilang.parser2 :as p])
  (:require [minilang.prettyprint :as pp])
  (:require [minilang.astbuilder :as ast])
  (:require [minilang.scope :as s])
  (:require [minilang.analyzer :as analyzer]))

(declare generate-code)

; Recursively generate code for all children of given augmented AST node.
(defn recur-on-children [aast]
  (loop [nodes (node/children aast)]
    (if (not (empty? nodes))
      (do
        (generate-code (first nodes))
        (recur (rest nodes))))))

(defn handle-expression-statement [aast]
  (generate-code (node/get-child aast 0))
  (if (node/has-prop? aast :last)
    (do
      (println "\tsyscall $println")
      (println "\tpop"))
    (println "\tpop"))
  )

(defn handle-int-literal [aast]
  (let [literal-val (:value aast)]
    (println "\tldc_i" literal-val)))

(defn handle-op-assign [aast]
  (let [child (node/get-child aast 0)
        regnum-val (node/get-prop child :regnum)]
    (do
      (generate-code (node/get-child aast 1))
      (println "\tdup")
      (println "\tstlocal" regnum-val))))

 (defn handle-op-add [aast]
   (do
     (generate-code (node/get-child aast 0))
     (generate-code (node/get-child aast 1))
     (println "\tadd")))
 
 (defn handle-op-mul [aast]
   (do
     (generate-code (node/get-child aast 0))
     (generate-code (node/get-child aast 1))
     (println "\tmul")))
 
 (defn handle-identifier [aast]
   (let [regnum-val (node/get-prop aast :regnum)]
   (println "\tldlocal" regnum-val)))

(defn generate-code [aast]
  ;(println (str "; at " (:symbol aast)))
  (case (:symbol aast)
    :statement_list (recur-on-children aast)
    :expression_statement (handle-expression-statement aast)

    :int_literal (handle-int-literal aast)
    :op_assign (handle-op-assign aast)
    :op_plus (handle-op-add aast)
    :op_mul (handle-op-mul aast)
    :identifier (handle-identifier aast)
    
    ; Default case: do nothing
    ;(println (str "; ignored " (:symbol aast)))
    nil
    ))

(defn compile-unit [unit]
  (let [stmt-list (node/get-child unit 0)
        nlocals (node/get-prop stmt-list :nlocals)]
    (do
      ; This is the program entry point
      (println "main:")
      ; Reserve space for local variables
      (println (str "\tenter 0, " nlocals))
      ; Generate code for the top-level statement list
      (generate-code stmt-list)
      ; Emit code to return cleanly (to exit the program)
      (println "\tldc_i 0")
      (println "\tret"))))


; ----------------------------------------------------------------------
; Testing:
; Edit testprog, then reload this file, then execute
;
;   (compile-unit aast)
;
; in a REPL.
; ----------------------------------------------------------------------

;(def testprog "1; 2; 3; 42;")
;(def testprog "var a; a:= 4 + 2; a:= a + 6;")
;(def testprog "var a; var b; var c; b := 6; c := 3; a := b*c;")
(def testprog "var a; var b; var c; b := 6; c := 3; a := b*c;")
(def parse-tree (p/parse (lexer/token-sequence (lexer/create-lexer (java.io.StringReader. testprog)))))
(def ast (ast/build-ast parse-tree))
(def aast (analyzer/augment-ast ast (s/create-scope)))
