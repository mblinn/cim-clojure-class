(ns cim-clojure-class.1-homework)

(def _ nil)

;; Basic Data Structures
(def a-vector [1 2 3])
(def a-map {:foo "foo" :bar "bar"})
(def a-list '("a" "b" "c"))

(= [1 2 3] a-vector)
(= _ a-map)
(= _ a-list)

(= _ (a-vector 0))
(= _ (a-map :bar))

(= _ (first a-list))
(= _ (rest a-list))

;; Functions
(defn add-two-nums [a b] (+ a b))

(= (add-two-nums _ _) 42)

;your prepend-with-hello function goes here.
(= (prepend-with-hello _) "Hello, mike")

;; Read/Eval
(= _ (read-string "[:foo :bar :baz]"))

(= _ (read-string "(+ 1 1)"))
(= _ (eval (read-string "(+ 1 1)")))