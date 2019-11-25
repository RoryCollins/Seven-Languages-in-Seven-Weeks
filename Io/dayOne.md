# Day One

## Answer

*One*. Io is strongly typed.
```
Io> 1 + 1
==> 2
Io> 1 + "one"

  Exception: argument 0 to method '+' must be a Number, not a 'Sequence'
  ---------
  message '+' in 'Command Line' on line 1

```
*Two*. true/false as below
```
Io> 0 and true
==> true
Io> "" and true
==> true
Io> nil and true
==> false
``` 
*Three*. You can find out the slots a prototype supports by calling `slotNames` on the `proto` object:
```
Io> Vehicle := Object clone
Io> Vehicle description := "Something to take you places"
Io> Car := Vehicle clone
Io> Car slotNames
==> list("type")
Io> Car proto slotNames
==> list("type", "description")
```

*Four*. `::=` is the `newSlot` assignment operator, and `:=` is the `setSlot` operator.
The difference is that `newSlot` also provides a setter for that slot. For instance:
```Io
Animal := Object clone
Animal legs ::= nil
Animal hasTail ::= nil
Cat := Animal clone setLegs(4) setHasTail(true)
```
As you can see, this makes constructors more readable.