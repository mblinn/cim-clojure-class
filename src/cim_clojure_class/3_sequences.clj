(ns cim-clojure-class.3-sequences
  (:require [clojure.string :as string]))

;;; It is better to have 100 functions operate on one data structure than 10 functions on 10 data structures. 
; Alan J. Perlis

;;; Lesson plan for today.
;; Learn about Clojure's sequence abstraction and a few of the functions it operates over.
;; Along the way, introduce destructuring binding and Clojure's java interop. 

;;; Sequence Interface (ISeq)
; Three basic operations, first, rest, cons (just an abstraction of operations on a singly linked list)!

;(first '(:foo :bar :baz))
;(first [:foo :bar :baz])

;(rest '(:foo :bar :baz))
;(rest [:foo :bar :baz])

;(cons :foo '(:bar :baz))
;(cons :foo [:bar :baz])

;; Many of Clojure's built in data types know how to turn themselves into something that implements ISeq.
;(seq [:foo :bar :baz])
;(seq {:foo "1" :bar "2"})

;;; From this simple interface, an extremely rich sequence library is built!

;;Filter
; Filter takes a predicate function and a seq-able thing.  It returns a new sequence containing only the items for which the predicate returned true.
; (filter odd? [1 2 3 4])
; (filter odd? '(1 2 3 4))

;; What about maps?
; (into {} [[:foo "foo"]])
; (into [] '(:foo :bar))

; This is an example of a 'destructuring bind'.
(defn destructuring-example [[first second third]]
  {first "first-key" second "second-key" third "third-key"})

(defn destructuring-map [{a-value :a-key second-value :second-key}]
  [a-value second-value])

(defn destructuring-map-let [a-map]
  (let [{a-value :a-key second-value :second-key} a-map]
    [a-value second-value]))

; It is idomatic to use _ when we don't care about a particular value.
(defn odd-value [[_ value]]
     (odd? value))

(defn filter-odd-values [m]
  (into {} (filter odd-value m)))

(def vowel? #{\a \e \i \o \u}) 
(defn vowels-in-word [word] 
  (set (filter vowel? word)))

; Observations - filter is declarative, a little bit like a where clause in SQL.
; Usually more concise and easier to understand than the iterative version as it's more concered with *what* to do rather than *how* it's done.

;;Map
; Map takes a function and a sequ-able thing, and returns a new sequence where the function has been applied to every element of the original sequence.
;(map inc [1 2 3 4])
;(map inc '(1 2 3 4))

(defn uppercase-name [name]
  """
  Uppercases the first letter in every section of a name.
  IE: mike linn -> Mike Linn
  """
  (let [name-parts (string/split name #" ")
        uppercase-part (fn [name-part] (str (Character/toUpperCase (.charAt name-part 0)) (.substring name-part 1 (.length name-part))))]
    (apply str (interpose " " (map uppercase-part name-parts)))))

(defn uppercase-names [names]
  (map uppercase-name names))

(defn uppercase-name-values [names-map]
  (into {} (map (fn [[key value]] [key (uppercase-name value)]) names-map)))
  
;;Reduce (foldl)
; Reduce takes a function of two arguments and a seq-able thing.  The function is known as a 'reducing function'.  It is used to combine the elements of the sequence in turn.

; (reduce + [1 2 3 4 5])

(defn calculate-discount [prices]
  (reduce + 
          (map (fn [price] (* price 0.10))
               (filter (fn [price] (> price 20))
                       prices))))

(defn calculate-discount-cleaner [prices]
  (let [price-filter (fn [price] (> price 20))
        discounter (fn [price] (* price 0.10))]
    (reduce + (map discounter (filter price-filter prices)))))
          

;;For (sequence/list comprehension, or List monad if you're into the whole Monad thing)


