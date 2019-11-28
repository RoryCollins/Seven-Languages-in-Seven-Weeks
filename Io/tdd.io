assertEqual := method(expected, actual, if(
    actual == expected,
    "Pass" println,
    Exception raise("expected: " .. expected .. " | received: " .. actual )))
