(ns minilang.astbuilder
  (:require [minilang.lexer :as lexer])
  (:require [minilang.parser2 :as p])
  (:require [minilang.prettyprint :as pp])
  (:require [minilang.node :as node]))

; Forward declaration for the build-ast function, useful if you
; define any helper functions that need to call build-ast.
;
(declare build-ast)

; Flatten a :statement_list node.
; The resulting AST node should have the ASTs for the statements
; that are part of the statement list as immediate children.
;
; Parameters:
;   node - a :statement_list parse node
;
; Returns: the statement list as an AST, with child statements
; as immediate children
;
(defn flatten-statement-list [node]
  (loop [x node
         acc []]
    (if(= (node/num-children x) 1)
      ;base case
      (node/make-node :statement_list (map build-ast (conj acc (node/get-child x 0))))
      (recur (node/get-child x 1) (conj acc (node/get-child x 0))))))
      
; Returns an AST node whose symbol is the same as the parse node,
; and whose children are ASTs constructed from the children of the
; parse node.
;
; Parameters:
;    node - a parse node
;
; Returns: an AST node (as described above)
;
(defn recur-on-children [node]
  (node/make-node (:symbol node) (map build-ast (node/children node))))

; Build an Abstract Syntax Tree (AST) from the specified
; parse tree.
;
; Parameters:
;    node - a parse tree
;
; Returns: the abstract syntax tree representation of the parse tree.
;
(defn build-ast [node]
  (case (:symbol node)
    :unit (recur-on-children node)
    :statement_list (flatten-statement-list node)
    :statement (build-ast (node/get-child node 0))
    :var_decl_statement (node/make-node :var_decl_statement [(node/get-child node 1)])
    :expression_statement 
    node))

; ----------------------------------------------------------------------
; Testing
; ----------------------------------------------------------------------

(def testprog "var a; a := 3*4;")
;(def testprog "a * (b + 3);")
;(def testprog "while (a <= b) { c; d*e*4; }")
;(def testprog "if (x != 4) { y := z*3; }")

(def parse-tree (p/parse (lexer/token-sequence (lexer/create-lexer (java.io.StringReader. testprog)))))
(def ast (build-ast parse-tree))
