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

; The first line of the output -- hello world -- is the what the function printed to the console.
; Notice the -- nil -- on the line after it?  That's the value of the function.

; By the way, REPL stands for Read Eval Print Loop.
; You might think it's just like a shell from Ruby, or Python, or Javascript, or your favorite language that's not Java.
; In a sense it is, it serves similar means as an interactive development environment.
; But in a sense it's different.  Read and Eval have very specific meaning in Lisp that aren't quite the same in other languages.
; I'm not going to tell you what they are yet, so as to build a sense of mystery and tension. 
; But they are central to what it means to be "A Lisp" or a language in the Lisp family. 

; By the way again, Read and Eval are closely related to why Lisp looks so... different... than other languages.
; So remember them. We'll get back to them at the end.

; By the way one more time.  This Read and Eval related strange syntax isn't incidental, it enables some very powerful
; stuff that can't be done in languages with more complex syntax.  But we'll get to that as well.

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

; Clojure also has ratios.
;--> 42/41

; Another import data type - keywords.
; Keyword start with a colon, like the following.
;--> :foo
; For the rubyists in the room, they're a lot like ruby symbols.  They can be used as enumerated values, keys in maps, etc.
; Anything where you need to do a fast equality comparison.

; Finally, there's a set of aggregate data structures.
; It's got maps, vectors and sets (and lists, but we'll cover those later).
; Here's a map, vector and a set.
;--> {:foo "a foo", 42 "a fourty two"}
;--> [1 2 3 "orange"]
;--> #{"dingo" "baby" :tree 42}

; Clojure collections a heterogenous, you can store different types in a single collection.
; These aggregate collections *are not* implemented as Java collections, though they serve a similar purpouse.  
; They are efficient, immutable datastructures.  They are almost magical.  We'll get to that next time.

; Let's take a look at maps in a bit more detail first, with a quick detour into naming things.
; The following code snippet creates a map, and binds the name "a-map" to it.
(def a-map {:foo "foo" :bar "bar" :baz "baz"})

; You can think of this as something like a Java reference to the map, with one exception.
; It's not a *mutable* reference.  It's not a variable.  You're *binding* a name to a map.  

; While you *can* do something like this.
(def something "a thing")
(def something "another thing")
; It's only to make things easy in the REPL.  def is not assignment.  It's a one time binding of
; a *name* to a *value*.  (This isn't the whole story on def, but it's a good start to it)

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
; Clojure maps use a very efficient, novel datastructure.  It's 2-4 times slower than Java's mutable data structures to
; add to, and uses a bit more memory.  That's pretty darn good for a completely immutable datastructure!

; Vectors are a bit like an immutable version of ArrayList.  They grow dynamically and you can index into them in (near) constant time.
(def a-vector [1 2 3])

; Vectors are a function of their indices.
;--> (a-vector 0)

; conj (short for conjoin) lets us add to a vector 
; (conj a-vector 4)

; Sets are mathematical sets.
(def a-set #{:foo :bar :baz})
; They are also functions.  They return the value if the set contains the passed in value.
;--> (a-set :foo)

; We can create a new set with additional values using conj.
;--> (conj a-set :quz :foo)

; We've seen one function definition before, let's take look at a couple more.

; Here's a simple function definition with two arguments.
; It's got a docstring, and its value is a string.  Try running it now.
; This is a *pure* function.  It's a function in the mathematical sense.
; It has no side effects, and does not mutate any state.
(defn a-function [an-argument another-argument]
  "A simple function."
  (str "this is an argument " an-argument " and another " another-argument))

; Here's another function.  It is not a pure function, because it prints to the console.
(defn side-effectful [an-argument]
  "A simple function with a let statement and side effect."
  (let [a-string (str "an-argument " an-argument)]
    (println a-string) ;<--- this is a side effect
    a-string))

; Clojure has several useful conditionals
; Let's look at two of them now.

; This is an if expression. It takes the form:
; (if 
;    predicate
;    value-if-true
;    value-if-false)
(defn half-if-even [num]
  "Divdes the number by two if it's even,
   otherwise returns it as is."
  (if (even? num)
    (/ num 2)
    num))

; The other conditional we'll cover now is cond.
; cond is useful when we need to chose between more than two options.

(defn get-job-type [name]
  "Given a name, get a job type."
  (cond 
    (= "MBL" name) :architect
    (= "Mishu" name) :troll
    (= "Trevor" name) :developer
    (= "Jon" name) :architect
    :else :unknown))

; There are other conditionals, but these two are a good start. 
; One final note on conditionals, Clojure treats false and nil as "false" values.
; Everything else is true.
; (For those of you with Lisp experience, nil is not the empty list in Clojure, it's basically just Java's null).

; Okay, so that's all pretty standard stuff.  One thing that you may not have noticed, however
; is that everything we've seen above is an *expression*.  That is, everything has a value.  
; For instance;
;--> (if true "truthy" "falsey")
; Has the value "truthy".  This is quite different from a language like Java where most of the conditionals
; are *statements*.  An if statement has no value in Java, it's executed purely for side effects;

  