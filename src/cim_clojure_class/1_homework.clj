(ns cim-clojure-class.1-homework)

(def _ nil)

;; Basic Data Structures
(def a-vector [1 2 3])
(def a-map {:foo "foo" :bar "bar"})
(def a-list '("a" "b" "c"))

(= [1 2 3] a-vector)
(= {:foo "foo" :bar "bar"} a-map)
(= '("a" "b" "c") a-list)

(= 1 (a-vector 0))
(= "bar" (a-map :bar))

(= "a" (first a-list))
(= '("b" "c") (rest a-list))

;; Functions
(defn add-two-nums [a b] (+ a b))

(= (add-two-nums 40 2) 42)

;your prepend-with-hello function goes here.
(defn prepend-with-hello [name]
  (str "Hello, " name))
(= (prepend-with-hello "mike") "Hello, mike")

;; Read/Eval
(= [:foo :bar :baz] (read-string "[:foo :bar :baz]"))

(= '(+ 1 1) (read-string "(+ 1 1)"))
(= 2 (eval (read-string "(+ 1 1)")))