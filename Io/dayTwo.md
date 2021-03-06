# Day Two

## Do:

### One 
Implement the Fibonacci Sequence with recursion and with loops
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

### Two
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

### Three
Write a program to add up all the numbers in a two-dimensional array
```Io
sumAll := method(numberSequences, numberSequences flatten sum)

twoDimensionalArray := list(
    list(1,2),
    list(3,4)
)

sumAll(twoDimensionalArray) println
```

### Four
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

### Five

Write a prototype for a two-dimensional list, with `dim(x, y)`, `get(x, y)`, `set(x, y, value)` and `transpose()` methods

### Six - Optional

Add a `transpose()` method that transposes the matrix such that `matrix get(x, y)` == `matrix transpose() get(y, x)`

### Seven

Add functionality to read and write the matrix to a file  

```Io
doFile("tdd.io")

range := method(start, end, seq := list clone; for(i, start, end, seq append(i)); seq)

Matrix := Object clone do(
    contents ::= List clone
    x ::= nil
    y ::= nil

    dim := method(x, y, (
        self setContents(range(0, y-1) map(currentRow := List clone setSize(x)))
        self setX(x)
        self setY(y)
    ))

    get := method(x,y, (
        xs := (self contents) at(y);
        xs at(x)
    ))

    set := method(x, y, value, (
        xs := (self contents) at(y);
        xs atPut(x, value)
    ))

    transpose := method(
        newMatrix := Matrix clone
        newMatrix dim(self y, self x)
        range(0, (self x)-1) map(x,
            range(0, self y-1) map(y,
                newMatrix set(y, x, self get(x, y))
            )
        );
        newMatrix
    )

    toFile := method(name, File with(name) open write(self serialized) close)

    fromFile := method(name, doRelativeFile(name))

    == := method(other, (
        (self x) == (other x) and
        (self y) == (other y) and
        (self contents) == (other contents)
        )
    )
)

matrix := Matrix clone
matrix dim(4,2)
matrix set(2,0,5)

matrix toFile("foo.txt")

read_matrix := Matrix fromFile("foo.txt")

assertEqual(5, matrix get(2, 0))                # Pass
assertEqual(5, (matrix transpose) get(0, 2))    # Pass
assertEqual(matrix, read_matrix)                # Pass
```

### Eight
Write a program that gives you ten tries to guess a random number from 1-100. If you would like, give a hint 
of `hotter` or `colder` after the first guess.

```Io
secretNumber := (Random value(1, 100) round);
tries := 0
lastDistance := nil
currentDistance := nil
found := false

while((tries < 10) and (found not),
  tries = tries + 1
  guess := File standardInput readLine("Enter your guess: ") asNumber

  currentDistance := (secretNumber - guess) abs
  if(currentDistance == 0) then (
    found = true
  ) else (
    if (lastDistance == nil,
        "Try again" println,
        if(currentDistance < lastDistance, "Hotter" println, "Colder" println))
    lastDistance = currentDistance
  )
)

if(found, "You found the secret number!", "Sorry, you ran out of guesses.") println
```


## Appendix
`assertEqual(expected, actual)` is defined in `tdd.io` as the following:
```Io
assertEqual := method(expected, actual, if(
    actual == expected,
    "Pass" println,
    Exception raise("expected: " .. expected .. " | received: " .. actual )))
``` 