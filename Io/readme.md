# Io
## Day one
### Syntax

`Receivers` go on the left, `messages` on the right.
See the following example:
```Io
"Hey ho, Io" print
```
In this example, the string `"Hey ho, Io"` is the receiver, and `print` is the message that we send to that receiver

### Objects

We create an object by cloning the root, `Object`.
```Io
Vehicle := Object clone
```

An object is just a collection of `slots` (think key-value pairs). We can define a new slot with the assignment operator `:=`

```Io
Vehicle description := "Something to take you places"
```

We can check the value of that slot by calling the object followed by the name of the slot:

```Io
Vehicle description #Something to take you places
```

We can override a value with the assignment operator `:=` or we can simply use `=`. The latter will fail if a slot with that name does not already exist.

To list the slot names for an object, we can send the message `slotNames`
```Io
Vehicle slotNames #list(type, description)
```
Note that Vehicle has a `type` slot. This is because, by convention, `types` begin with an upper case letter. In this example the `type` of Vehicle is "Vehicle".  

A `Type` is just an object with a `type` slot 

### Inheritance

Having seen how we can create types, let's try to create a *ferrari* that is an instance of a *car*, which is a *Vehicle*.

First up, creating the type car.
```Io
Car := Vehicle clone
Car slotNames #list(type)
Car description #Something to take you places
```
Note that `description` does not exist on the type `Car`, so Io sends the message to the `prototype`, and finds the slot in `Vehicle`. Carrying on...
```Io
ferrari := Car clone
ferrari slotNames #list()
ferrari type #Car
ferrari description #Something to take you places
```
`ferrari` begins with a lower case letter, so Io knows that it is an instance (not a type) and therefore there is no `type` slot on this object. As before, when we send the message `type` to ferrari, this message is sent to the `prototype`, where the slot is found in `Car`.

### Lists and Maps

A list can be created like so:
```Io
toDos := list("find my car", "find Continuum Transfunctioner")
```
the `List` type has a bunch of methods, such as `size` and `append`. Try `List slotNames` to see more.

A `map` is a collection of key-value pairs.

```Io
elvis := Map clone
elvis atPut ("home", "Graceland")
elvis atPut ("style", "rock and roll")
```

Essentially maps work a lot like objects, so the `asObject` method is able to return the map as an abject (type Object).
Similarly, `asList` will return a list of lists, in the format `list(list(key1, value1), list(key2, value2))`

### Singletons

You can override the clone method of an object to return that object, thus preventing further instantiations of a type.
```Io
Highlander := Object clone
Highlander clone := Highlander

fred := Highlander clone
mike := Highlander clone
fred == mike
```

Just be careful about overriding methods on the Object class, that might well screw you up

## Day two
### Loops

Infinite loops can be created with the `loop` keyword. The following code will execute until the interrupt flag `Ctrl-C` is sent.
```Io
loop("on and on and on" println)
```

`while` loops are syntactically simple, as below:

```
Io> i := 1
==> 1
Io> while(i < 11, i println; i = i + 1); "This one goes up to 11" println
1
2
...
10
11
This one goes up to 11
```
Note the semicolon, which is used here to concatenate two distinct messages.

`for` loops are generally simple, taking the form:
 `for(iterator, start, finish, increment?, action)`
 
 For instance:
```Io
for(i, 1, 11, i println); "This one goes up to 11" println
for(i, 1, 11, 2, i println); "This one goes up to 11" println
```
The first for loop gives the same output as the above `while` loop, whereas the second loop goes up in increments of 2, giving 
```
1
3
5
7
9
11
This one goes up to 11
```
Note that the fourth parameter is optional. This means there is a subtle pitfall when supplying extra arguments (which Io will allow).

Note the following loops:
```Io
for(i, 1, 11, 2, i println, "extra argument")
for(i, 1, 11, i println, "extra argument")
```
The first `for` loop here will print the same incremented output as before, ignoring the extra argument, however the parameters for the second loop are not parsed correctly, resulting in the following output:
```
==> 11
extra argument
```

### Conditionals

`if` statements take the form `if(conditional, trueBlock, falseBlock)`

Not a lot else to say about that really. 

### Operators

For a list of operators and their order of precedence, use the `OperatorTable` command.
To add a new operator, call `addOperator` with the operator that you want to add, and its desired position in the `OperatorTable`
```Io
OperatorTable addOperator("xor", 11)
true xor := method(bool, if(bool, false, true))
false xor := method(bool, if(bool, true, false))
```
The above code provides an `xor` operator, that can be used as below:
```Io
true xor true #false
true xor false #true
false xor true #true
false xor false #false
```
### Messages

### Reflection