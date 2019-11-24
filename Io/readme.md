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

```
Io> Vehicle description
Something to take you places
```

We can override a value with the assignment operator `:=` or we can simply use `=`. The latter will fail if a slot with that name does not already exist.

To list the slot names for an object, we can send the message `slotNames`
```
Io> Vehicle slotNames
list(type, description)
```
Note that Vehicle has a `type` slot. This is because, by convention, `types` begin with an upper case letter. In this example the `type` of Vehicle is "Vehicle".  

A `Type` is just an object with a `type` slot 

### Inheritance

Having seen how we can create types, let's try to create a *ferrari* that is an instance of a *car*, which is a *Vehicle*.

First up, creating the type car.
```
Io> Car := Vehicle clone
Io> Car slotNames
list(type)
Io> Car description
Something to take you places
```
Note that `description` does not exist on the type `Car`, so Io sends the message to the `prototype`, and finds the slot in `Vehicle`. Carrying on...
```
Io> ferrari := Car clone
Io> ferrari slotNames
list()
Io> ferrari type
Car
Io> ferrari description
Something to take you places
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