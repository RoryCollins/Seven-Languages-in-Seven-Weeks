fibWithRecursion := method(number, (
    result := if(
        (number == 1) or (number == 2),
        1,
        fibWithRecursion(number-1)+fibWithRecursion(number-2))
    )
)

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
    current println
))

fibWithRecursion(8)