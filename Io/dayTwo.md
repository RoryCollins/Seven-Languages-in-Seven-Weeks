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