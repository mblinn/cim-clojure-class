(ns cim-clojure-class.1-the-beginning)

;; Hi!  
; Thanks for coming.
; Here, have a hello world function.
(defn hello-world [] 
  (println "hello, world"))

; You run it like this.
; (hello-world)
; It prints hello, world to the console.
; But first you need to load this file into the REPL.  
; I'll show you how to do that using Eclipse now.
; Dramatic pause...

; Okay, now give it a shot.
; You should see output like the following.
; => (hello-world)
; hello, world
; nil

; The first li...

; By the way, REPL stands for Read Eval Print Loop.
; You might think it's just like a shell from Ruby, or Python, or Javascript, or your favorite language that's not Java.
; In a sense it is, it serves similar means as an interactive development environment.
; But in a sense it's different.  Read and Eval have very specific meaning in Lisp that aren't quite the same in other languages.
; I'm not going to tell you what they are yet, so as to build a sense of mystery and tension. 
; But they are central to what it means to be "A Lisp" or a language in the Lisp family. 

; By the way again, Read and Eval are closely related to why Lisp looks so... different... than other languages.
; So remember them. We'll get back to them at the end.

; For now, let's start with the basics. As we go, we'll see that some of these comments start with ;-->.
; That means the following text is something you can paste into the REPL and play with.  

; Clojure has the data types you'd expect.
; Here's an integer, double, string and char.
;--> 42
;--> 42.42
;--> "hi"
;--> \a

; These are just Java objects.
;--> (type 42)
;--> (type "")

; Clojure also has a set of its own aggregate data structures.
; It's got maps, vectors and sets (and lists, but we'll cover those later).
; Here's a map, vector and a set.
;--> {:foo "a foo", 42 "a fourty two"}
;--> [1 2 3 "orange"]
;--> #{"dingo" "baby" :tree 42}

; Clojure collections a heterogenous, you can store different types in a single collection.
; These aggregate collections *are not* implemented as Java collections.
; They are efficient, immutable datastructures.  They are almost magical.  We'll get to that next time.

; There's one ... keywords. 

; Let's take a look at maps in a bit more detail first, with a quick detour into naming things.
; The following code snippet creates a map, and binds the name "a-map" to it.
(def a-map {:foo "foo" :bar "bar" :baz "baz"})

; You can think of this as something like a Java reference to the map, with one exception.
; It's not a *mutable* reference.  It's not a variable.  You're *binding* a name to a map.  

; While you *can* do something like this.
(def something "a thing")
(def something "another thing")
; It's only to make things easy in the REPL.  def is not assignment.  It's a one time binding of
; a *name* to a *value*.  (This isn't 100% correct, but it's a good start).

; Let's play with a-map.
;--> a-map

; If we want to get something out of it, we can use it as a function.
;--> (a-map :foo)

; We can also use a keyword as a function.  It will look itself up in the passed in map.
;--> (:foo a-map).
; This seems to be the most common way of doing map lookups in Clojure. 

; Maps are not mutable.  To "add" a key/value pair to a map, we use assoc.
; This returns a new map with the new keys.  It leaves the original map untouched.
;--> (assoc a-map :quz "quz")
;--> a-map
; This uses a very efficient, novel datastructure.  It's 2-4 times slower than Java's mutable data structures to
; add, and uses a bit more memory.  That's pretty darn good for a completely immutable datastructure!

; Vectors ca
(def a-vector [1 2 3])

;--> (a-vector 0)

; Conj...
; (conj a-vector 4)

; Sets
(def a-set #{:foo :bar :baz})
;--> (a-set :foo)

;--> (conj a-set :quz :foo)

; functions
(defn a-function [an-argument]
  "A simple function."
  (str "this is an argument " an-argument))

(defn side-effectful [an-argument]
  "A simple function with a let statement and side effect."
  (let [a-string (str "an-argument" an-argument)]
    (println a-string)
    a-string))

; conditionals
(defn half-if-even [num]
  "Divdes the number by two if it's even,
   otherwise returns it as is."
  (if (even? num)
    (/ num 2)
    num))

(defn get-job-type [name]
  "Given a name, get a job type."
  (cond 
    (= "MBL" name) :architect
    (= "Mishu" name) :troll
    (= "Trevor" name) :developer
    (= "Jon" name) :architect
    :else :unknown))

; read
(def read-vec (read-string "[1 2 3]"))
(def read-map (read-string "{:foo \"foo\" :bar \"bar\"}"))

; eval
; vars
(def a-var "this is the value")

; quote
;--> (quote (+ 1 1))
;--> '(+ 1 1)

; lists
;--> ("hello" "world")
; ClassCastException java.lang.String cannot be cast to clojure.lang.IFn  ...
;--> '("hello" "world")
(def hello-list '("hello", "world"))
(def fn-list '(hello-world))
;--> (type hello-list)
;--> (type fn-list)

;--> (eval hello-list)
;--> (eval fn-list) 

(defn make-helloer [name]
  "Take a name as a string and creates a function that says hello to that name.
   ie: (make-helloer \"jack\") creates the function (hello-jack)" 
  (let [fn-name (symbol (str "hello-" name))
        fn-form (list 'defn fn-name [] 
                      (list 'println (str "hello " name)))]
        (eval fn-form)
        fn-form))

(defn make-helloer-two [name]
  "Same as make-helloer, but uses syntax quote."
  (let [fn-name (symbol (str "hello-" name))
        fn-form `(defn ~fn-name [] (println ~(str "hello " name)))]
    (eval fn-form)
    fn-form))

(defmacro make-helloer-macro [name]
  "Macro version of make-helloer"
  (let [fn-name (symbol (str "hello-" name))]
    `(defn ~fn-name [] (println ~(str "hello " name)))))
  