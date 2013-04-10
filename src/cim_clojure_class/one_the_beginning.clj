(ns cim-clojure-class.one-the-beginning)

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

; You can think of this as something like a Java reference to the 
; This isn't entirely true, but it's good enough for now.  


