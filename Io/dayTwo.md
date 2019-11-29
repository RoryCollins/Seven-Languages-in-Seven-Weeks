# Day Two

## Do:

*One*. Implement the Fibonacci Sequence with recursion and with loops
```Io
fib := method(number, (
    result := if(
        (number <= 2),
        1,
        fib(number-1) + fib(number-2))
    )
)

fibWithRecursion := method(number, fib(number))

fibWithLoop := method(number, (
    index := 3;
    current := 1;
    last := 1;
    while(
        index <= number, (
            new := current + last;
            last = current;
            current = new;
            index := index + 1
         )
    )
    current
))

("Using recursion, the 7th element in the fibonacci sequence is " .. fibWithRecursion(7)) println   #13
("Using loops, the 8th element in the fibonacci sequence is " .. fibWithLoop(8)) println            #21

```

*Two*.
Override the `/` operator to return 0 when the denominator is zero.
```Io
Number dividedBy := Number getSlot("/")
Number / := method(other,
    if(other == 0,
        0,
        (call target) dividedBy(other)))
(4 / 5) println  # 0.8
(3 / 0) println  # 0
```
In the above, `(call target)` can be replaced by `self`

*Three*
Write a program to add up all the numbers in a two-dimensional array
```Io
sumAll := method(numberSequences, numberSequences flatten sum)

twoDimensionalArray := list(
    list(1,2),
    list(3,4)
)

sumAll(twoDimensionalArray) println
```

*Four*
myAverage
```Io
doFile("tdd.io")

List myAverage := method(
    total :=  self reduce(
        total, 
        current, 
        if((current type) == "Number", 
            total + current, 
            Exception raise(current .. " is not a Number")), 
        0);
    total / (self size))

assertEqual(4, (list(3,4,5) myAverage))         # Pass
assertEqual(20, (list(10,15,35) myAverage))     # Pass
assertEqual("??", (list(10,"a",35) myAverage))  # Exception: "a" is not a Number
```

*Five* & *Six*
Write a prototype for a two-dimensional list, with `dim(x, y)`, `get(x, y)`, `set(x, y, value)` and `transpose()` methods


```Io
doFile("tdd.io")

Number to := method(end, range := List clone; for(i, self, end-1, range append(i)); range)

Matrix := Object clone
Matrix contents ::= List clone
Matrix x ::= nil
Matrix y ::= nil

Matrix dim := method(x, y, (
    ys := List clone;
    for(i, 1, y, (
        xs := List clone;
        for(i, 1, x, (
            xs append(0)
        ));
        ys append(xs)
    ));
    self setContents(ys)
    self setX(x)
    self setY(y)
))


Matrix get := method(x,y, (
    xs := (self contents) at(y);
    xs at(x)
))

Matrix set := method(x, y, value, (
    xs := (self contents) at(y);
    xs atPut(x, value)
))

Matrix transpose := method(
    newMatrix := Matrix clone;
    newMatrix dim(self y, self x);
    (0 to(self x)) foreach(x, (
        (0 to(self y)) foreach(y, (
        newMatrix set(y, x, (self get(x, y)))
        ))
       ));
    newMatrix
)

matrix := Matrix clone
matrix dim(4,2)
matrix set(3,1,5)

assertEqual(5, matrix get(3, 1))
transposed_matrix := matrix transpose()
assertEqual(5, transposed_matrix get(1, 3))
```


## Appendix
`assertEqual(expected, actual)` is defined in `tdd.io` as the following:
```Io
assertEqual := method(expected, actual, if(
    actual == expected,
    "Pass" println,
    Exception raise("expected: " .. expected .. " | received: " .. actual )))
``` 