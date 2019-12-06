# Day Two
## Find
Find some implementations of a Fibonacci series and factorials. How do they work?

#### Fibonacci
```Prolog
fib(0, 1) :- !.
fib(1, 1) :- !.
fib(N, Result) :-
    N1 is N - 1,
    N2 is N - 2,
    fib(N1, Result1),
    fib(N2, Result2),
    Result is Result1 + Result2.
```
In the above example, `N1` cannot be inlined, as that would prevent it from being evaluated (e.g., when `N` is 4,
 `N-1` is interpreted as `4-1`, not `3`). This has the effect of causing a stack overflow.
 
 `!` is the symbol for the `cut` operator, and that tells Prolog to stop interpreting.
 
#### Factorials
```Prolog
factorial(0, 1) :- !.
factorial(X, R) :- X1 is X-1, factorial(X1, S), R is S*X.
```

Again, the `!` is used here. Without this, Prolog would prompt you for the next result, at which point it would blow 
the stack.

## Do
### One
Reverse the elements of a list
```Prolog
reverseElements(Result, [], Result).
reverseElements(Result, [Head|Tail], CumulativeResult)      :- append([Head], CumulativeResult, NewResult), reverseElements(Result, Tail, NewResult).
reverseElements(Result, [Head|Tail])                        :- reverseElements(Result, Tail, [Head]), !.

reverseElements(Result, [1,2,3,4,5]). % Result = [5,4,3,2,1] yes
```

### Two
Find the smallest element of a list
```Prolog
smallestElement(Result, [], Result).
smallestElement(Result, [Head|Tail], CurrentSmallest)       :- Head < CurrentSmallest, smallestElement(Result, Tail, Head).
smallestElement(Result, [Head|Tail], CurrentSmallest)       :- Head >= CurrentSmallest, smallestElement(Result, Tail, CurrentSmallest).
smallestElement(Result, [Head|Tail])                        :- smallestElement(Result, Tail, Head), !.

smallestElement(Result, [2,3,1,5]). % Result = 1 yes
```

### Three
Sort the elements of a list
#### The wrong way

Implementing the `quickSort` algorithm in prolog:
```Prolog
partition(_, [], Lowers, Lowers, Highers, Highers).
partition(X, [Head|Tail], Lowers, CumulativeLowers, Highers, CumulativeHighers) :-
    Head =< X,
    append([Head], CumulativeLowers, NewLowers),
    partition(X, Tail, Lowers, NewLowers, Highers, CumulativeHighers).
partition(X, [Head|Tail], Lowers, CumulativeLowers, Highers, CumulativeHighers) :-
    Head > X,
    append([Head], CumulativeHighers, NewHighers),
    partition(X, Tail, Lowers, CumulativeLowers, Highers, NewHighers).
partition(X, List, Lowers, Highers) :- partition(X, List, Lowers, [], Highers, []).

quickSort([], []).
quickSort(Result, [Head|Tail]) :-
    partition(Head, Tail, Lowers, Highers),
    quickSort(LowerResult, Lowers),
    quickSort(HigherResult, Highers),
    append(LowerResult, [Head], FirstPart),
    append(FirstPart, HigherResult, Result),
    !.

quickSort(Result, [7,6,3,8,2,5,9]). % Result = [2,3,5,6,7,8,9] yes
```

#### The right way

On reflection, my mindset in the above was not set to `Prolog thinking`. All I need are the `rules` - i.e.
* The result must contain all of the elements of the original list
* Each element in the result must be less than or equal to the next element

This means I can approach the problem without even implementing a sorting algorithm:
```Prolog
isSorted([_|[]]).
isSorted([Head|[Head2|Tail]])           :- Head =< Head2, isSorted([Head2|Tail]), !.

allElementsMatched([], []).
allElementsMatched([Head|Tail], List2)  :- member(Head, List2), select(Head, List2, Remaining), allElementsMatched(Tail, Remaining), !.

sortElements(Sorted, Unsorted)          :- allElementsMatched(Sorted, Unsorted), isSorted(Sorted), !.

sortElements(Result, [7,6,3,8,2,5,9]). % Result = [2,3,5,6,7,8,9] yes
```

The trade-off here is the complexity of the algorithm vs control over the operation.