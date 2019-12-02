# Prolog
## Day one

### Installing

The version I'm using is `GNU Prolog 1.4.5`

Using `ubuntu 18.04`, install gprolog from source at [the gprolog website](http://www.gprolog.org/).

### About Prolog

The previous languages so far, `Io` and `Ruby`, have been `imperative` languages. 
That is, you tell the computer exactly how ot do a job, step by step.

`Prolog` is a `declarative` language. You give the computer some basic facts and inferences,
and let it do the the reasoning for you.  Similar to `SQL`, `Prolog` works on databases -
but the data consists of logical rules and relationships.

Prolog is made up of three building blocks: `facts`, `rules` and `queries`.

The `facts` and `rules` set up the *knowledge base*. A prolog compiler compiles the knowledge base into a 
form that's efficient for `queries`. 

### Basic facts
Looking at the file `friends.pl`:
```Prolog
likes(wallace, cheese).
likes(grommit, cheese).
likes(wendolene, sheep).

friends(X, Y) :- \+(X = Y), likes(X, Z), likes(Y, Z).
```
The first three statements are `facts`, and the last statement is a `rule`.

In this example, `wallace`, `grommit` and `wendolene` are `atoms`, and you can read the statement as 
'`wallace` likes `cheese`' and so on.

With the prolog interpreter, we can load and query the knowledge base.

```Prolog
['friends.pl']. % yes
likes(wallace, sheep).   % no
likes(grommit, cheese).  % yes
````

### Basic inferences and variables
The `friend` rule stipulates that two people are friends if:
* they are not the same person
* they both like the same thing.    

Trying the `friend` rule:
```Prolog
friend(wallace, grommit).    % yes
friend(grommit, wallace).    % yes
friend(wallace, wallace).    % no
friend(wendolene, wallace).  % no
```

### Filling in the blanks

We can also ask different types of questions:
```Prolog
likes(wallace, What).
% What = cheese
% yes

likes(Who, cheese). 
% Who = wallace ? ;
% Who = grommit ? ;
% no
```
In the first question, we ask Prolog to find some value for `What` that satisfies the query `likes(wallace, What).`
Prolog found one, `cheese` and then told us that it knows there are no more alternatives with the word `yes`

In the second question, we're told that `wallace` likes cheese, and then when we type `;` we asked prolog to find 
another. Prolog went away and found `grommit`, and prompted us for the next. Again we asked for another, and prolog 
returned with `no`.

#### Note
Why do I get the following? I would expect the interpreter to identify `wallace`
```Prolog
friend(Who, grommit). % no
friend(grommit, Who). % no
```
The problem here is that the statement `\+(X = Y)` forces unification to happen at the start of the tree, which is then 
negated. Pushing this statement to the end (i.e. `friend(X, Y) :- likes(X, Z), likes(Y, Z), \+(X = Y).`) results in the following:

```Prolog
friend(Who, grommit). 
% Who = wallace ? ;
% no
```

For a more detailed explanation, see [this Stack Overflow response](https://stackoverflow.com/a/39825461/1728649) 

### Where's the program?
With prolog, we don't actually create the algorithm - we express our logic in facts and inferences and then just ask
questions. Prolog is not about writing algorithms to solve logical problems. Prollog is avout describing your world as 
it is and presenting logical problems that your computer can solve.

### Unification

In the code:
```Prolog
cat(lion).
cat(tiger).

dorothy(X, Y, Z) :- X = lion, Y = tiger, Z = bear.
twin_cats(X, Y) :- cat(X), cat(Y).
```
The symbol `=` means `unify`, or 'find the values to make both sides match'.
Therefore:
```Prolog
dorothy(One, Two, Three).
% One = lion
% Three = bear
% Two =  tiger
```
and
```Prolog
twin_cats(One, Two).
```
gives
```text
One = lion
Two = lion ? a

One = lion
Two = tiger 

One = tiger
Two = lion 

One = tiger
Two = tiger

yes

```
