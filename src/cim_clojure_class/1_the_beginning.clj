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
; They are also functions.  They return true if the set contains the passed in value.
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
  (let [a-string (str "an-argument" an-argument)]
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

;; Diving Off The Deep End

; Okay then, let's discuss the Read in REPL.
; We're about to touch on some of the advanced features of Clojure briefly here.
; We'll come back to this at the end of the class, so don't worry if you don't grok it all now.
; Understanding this stuff, though, helps to understand two things:
; 1.) Why Clojure has the strange syntax it does, and why it can't really have a much more complicated one.
; 2.) Why there are still Lisp fanatics 50 years after it was invented.
; 3.) Why Clojure, and other Lisps, have the most powerful metaprogramming abilities of any language out there, and why it's unlikely that any other language can catch up.
; Well that was three things, I guess.  But whose counting.

; On to read.

; read takes in a sequence of characters representing a Clojure data structure, parses it and turns it into an actual Clojure data structure.
; For instance, here we are using a version of read that reads from a string to create a vector with the value [1 2 3]. 
(def read-vec (read-string "[1 2 3]"))

;--> read-vec

; here we are using it to create a map.
(def read-map (read-string "{:foo \"foo\" :bar \"bar\"}"))

;--> read-map

; read-string reads from 

; Anyhow, so that's what the Read in REPL means.  It reads the characters you typed in into Clojure data structures.

; So far, this seems kind of like Json, or YAML.  In a way it is, and Clojure programmers use it for the sorts of things
; you'd use Json or YAML for in other languages.  But that's not the whole story...  To tell it, we need to introduce eval.

; Eval takes a Clojure data structure and evaluates it to produce its value, according to a set of *evaluation rules*
; that tell it how to do so.

; Many data structures evalute to themselves.  For instance, keywords, numbers and strings.
;--> :foo
;--> 42
;--> "some jawn"

; Other data structures don't.  Let's take a closer look at def.
(def a-var "this is the value")
; def actually creates a data structure called a symbol, which is mainly used to name things, and binds it to some value.
; The evaluation rule for a symbol looks up the value the symbol is bound to.
;--> a-var
; (By the way, this *still* isn't the full story on def, that'll have to wait until week two.

; Evaluation is nifty, but sometimes you need to turn it off.  You can do this with quote.
; quote
;--> (quote (+ 1 1))
;--> (quote a-var)

; Or it's shorter form, a single quote.
;--> '(+ 1 1)

; By the way, earlier I said this "read takes in a sequence of characters representing a Clojure data structure, parses it and turns it into an actual Clojure data structure."
; Then I claimed that that "read" is the R in REPL.  Does something seem strange about that?  I said read converts a sequence of characters to Clojure data structures.
; But...
; But..
; Then what about all the *code* I've been typing into the REPL, like function definitions and if expressions?

; Let's talk about lists.
; This is a list. 
; ("hello" "world")
; It's a simple, singly linked list.
; It has a length of two.  Its first element is the string "hello".  Its second element is the string "world".
; Let's paste it into the REPL so we can see what Clojure does when it evaluates a list.
;--> ("hello" "world")
; ClassCastException java.lang.String cannot be cast to clojure.lang.IFn  ...

(defn usually [] "for some value of usually, which will be explained later.")

; Hmm well, okay then.  That didn't work.  
; What's up with that ClassCastException?  Well, clojure.lang.IFn is just a Java interface that Clojure uses to 
; represent functions.  And java.lang.String is just a Java String.  So Clojure just tried to cast a String to a 
; function?  Why would it do that?
; Well, it has to do with the evaluation rule for lists.  When Clojure evaluates a list it assumes that the first 
; element in the list is something that can be called, like a function, and the rest of the elements are arguments.
; It then evaluates those arguments
;--> (usually)
; and passes them in.
; So when Clojure tries to evaluate ("hello" "world"), it attempts to cast the string "hello" into a function and fails.

; Let's look at another list.  This is a list with three elements.  Remember, we can turn off evaluation with a single quote.
;--> '(+ 1 1)
; We can take the first element in it with "first".  It's the symbol "+";
;--> (first '(+ 1 1))
; We can also take the first element of the list ("hello" "world") we saw earlier in the same way.
;--> (first '("hello" "world"))
; 
; So here we've got two lists (+ 1 1) and ("hello" "world").  The first is clearly code that adds 1 and 1 together.
; The second is a list with two strings in it.

; Weird right?  Clojure code is Clojure data structures.
; For instance, this (defn foo [] "foo") is a list with four elements.
; The first is the symbol "defn", the second the symbol "foo", the third an empty vector and the fourth the string "foo".
; It's also, clearly, a function definition for a function named foo which always returns the string "foo".
; The fancy word for this sort of thing is "homoiconic".  You may also hear people refer to it as "code is data" 
; This description is trivially true in an uninteresting way since all code is data, it's just usually *text* data.
; In Clojure that textual representation maps cleanly onto Clojure's core data structures, lists, vectors and maps. 

; So what does that all mean?
; Well, since Clojure code is just Clojure data, we can manipulate it just as we'd manipulate any other Clojure data,
; and then call eval on it manually to evaluate it.

; For instance, here's a function that creates other functions.  
; It takes a person's name as a symbol, and creates a function that prints hello to them.
; You call it like this (make-helloer john)
; and it creates code that looks like this (defn hello-john [] (println "hello john")) then evaluates it.
; It also returns the code that it created, so you can see what it looks like.
(defn make-helloer [name]
  "Take a name as a string and creates a function that says hello to that name.
   ie: (make-helloer \"jack\") creates the function (hello-jack)" 
  (let [fn-name (symbol (str "hello-" name))
        fn-form (list 'defn fn-name [] 
                      (list 'println (str "hello " name)))]
        (eval fn-form)
        fn-form))

;--> (make-helloer 'your-name-here)

; That works, but it's a bit ugly.  Clojure provides another form of quote known as 
; "syntax quote", which turns off evaluation and allows us to selectively turn it back on inside of it
; using "unquote", which is represented as a tilde -- ~
; This allows us to create meta-code which looks much like a declarative template.  For instance, look 
; at how similar the following two lines look.
; `(defn ~fn-name [] (println ~(str "hello " name)))
; (defn hello-john [] (println (str "hello john")))
(defn make-helloer-two [name]
  "Same as make-helloer, but uses syntax quote."
  (let [fn-name (symbol (str "hello-" name))
        fn-form `(defn ~fn-name [] (println ~(str "hello " name)))]
    (eval fn-form)
    fn-form))

; Finally, we've got macros, which give us this power at compile time.  
; Basically, they let you write little extensions to the compiler!
(defmacro make-helloer-macro [name]
  "Macro version of make-helloer"
  (let [fn-name (symbol (str "hello-" name))]
    `(defn ~fn-name [] (println ~(str "hello " name)))))

; I've really breezed over this 

  