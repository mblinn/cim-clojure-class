(ns cim-clojure-class.2-read-eval)

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