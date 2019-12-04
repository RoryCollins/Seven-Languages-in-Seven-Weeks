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

## Day two
### Recursion

When dealing with lists and trees, it's more common to use recursion than iteration.

```Prolog
father(zeb, john_boy_sr).
father(john_boy_sr, john_boy_jr).

ancestor(X, Y) :- father(X, Y).
ancestor(X, Y) :- father(X, Z), ancestor(Z, Y).
```
`X` is an ancestor of `Y` if `X` is a father of `Y`.

`X` is an ancestor of `Y` if `X` is a father of `Z`, and `Z` is an ancestor of `Y`.

We can query this as expected, to find both ancestors and descendants.
```Prolog 
ancestor(zeb, Who).
% Who = john_boy_sr ? a
% Who = john_boy_jr

ancestor(Who, john_boy_jr).
% Who = john_boy_sr ? a
% Who = zeb
```

If you're not careful, each recursive call will add to the call stack, and you run the risk of `stack overflow`. Prolog
can optimise recursion with `tail call recursion`, whereby the final result can be calculated by the last call alone, 
meaning prolog is able to discard the call stack.  Without tail call recursion, the results of each call are needed to
calculate the answer, therefore the call stack cannot be discarded. 

See [here](https://stackoverflow.com/questions/33923/what-is-tail-recursion)
for more information on tail recursion.

### Lists & Tuples

Tuples are containers of fixed length, and can be specified as `(1,2,3)`

Lists are containers of variable length, and can be specified as `[1,2,3]`

#### Unification

Two tuples / lists unify only if they have the same number of elements and if each element unifies.
```Prolog
(1,2,3) = (1,2,3).      % yes
[1,2,3] = [1,2,3].      % yes

(1,2,3) = (1,2,3,4).    % no
[1,2,3] = [1,2,3,4].    % no

(1,3,2) = (1,2,3).      % no
[1,3,2] = [1,2,3].      % no

(A, 2, C) = (1, B, 3).  % A=1; B=2; C=3; yes
[A, 2, C] = [1, B, 3].  % A=1; B=2; C=3; yes

(2, 2, 3) = (X, X, Y).  % X=2; Y=3; yes
[2, 2, 3] = [X, X, Y].  % X=2; Y=3; yes

() = ().                % exception - can't have an empty tuple
[] = [].                % yes
```
In these examples, you can see that unification doesn't matter which side the variables are on.

Lists have a capability that `tuples` don't, the ability to be deconstructed with `[Head|Tail]`

Some examples:
```Prolog
[a, b, c] = [Head|Tail].            % Head=a; Tail=[b, c]; yes
[a] = [Head|Tail].                  % Head=a; Tail=[]; yes
[] = [Head|Tail].                   % no
[a, b, c] = [a|Tail].               % Tail=[b, c]; yes
[a, b, c] = [a|[Head|Tail]].        % Head=b; Tail=[c]; yes
[a, b, c, d, e] = [_, _|[Head|_]].  % Head=c; yes
```
`_` unifies with everything, so we told `Prolog` to skip the first two elements and split the 
rest into head and tail.

Note, the `|` is used to read the following as a list. So if we replace the `|` with a `,` before the `[Head...]` we get:
```Prolog
[a, b, c, d, e] = [_, _,[Head|_]].     % no
[a, b, [c, d, e]] = [_, _,[Head|_]].   % Head=c; yes
```

#### Lists and Math
These next examples make use of the `[Head|Tail]` destructuring, so they'll only work with `Lists`.

```Prolog
count(0, []).
count(Count, [Head|Tail]) :- count(TailCount, Tail), Count is TailCount + 1.

countWithTailRecursion(Count, [], Total) :- Count is Total.
countWithTailRecursion(Count, [Head|Tail], Total) :- countWithTailRecursion(Count, Tail, Total + 1).
countWithTailRecursion(Count, X) :- countWithTailRecursion(Count, X, 0).

sum(0, []).
sum(Total, [Head|Tail]) :- sum(Sum, Tail), Total is Head + Sum.

sumWithTailRecursion(Total, [], CumulativeTotal) :- Total is CumulativeTotal.
sumWithTailRecursion(Total, [Head|Tail], CumulativeTotal) :- sumWithTailRecursion(Total, Tail, CumulativeTotal + Head).
sumWithTailRecursion(Total, X) :- sumWithTailRecursion(Total, X, 0).

average(Average, List) :- sum(Sum, List), count(Count, List), Average is Sum/Count.
```
Here I've added a `withTailRecursion` method, so that the difference is made clear.

In either case, 
```Prolog
count(Count, [1, 2, 3]).                    % Count = 3 ? ; no 
countWithTailRecursion(Count, [1, 2, 3]).   % Count = 3 ? ; no 

sum(Sum, [1, 2, 3]).                        % Sum = 6 ? ; no 
sumWithTailRecursion(Sum, [1, 2, 3]).       % Sum = 6 ? ; no 

average(Average, [1, 2, 3]).                % Average = 2.0 ? ; no
```
