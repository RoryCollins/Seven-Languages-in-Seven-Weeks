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

We also have a `foreach` function too.
```Io
Object slotNames foreach(slotName, writeln(slotName))
``` 
This will write the name of every slot on the object method.

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

Just about everything in Io is a `message`.One of the crucial characteristics of Io is the ability to examine a message's components through `reflection`.

`Messages` are made up of three components, the `sender`, the `target` and the `arguments`.  The `sender` is the object that sent the message, and the `target` is the object that executes the message. You know what `arguments` are.
We can use the `call` message to examine each of these components for a message.

```Io
postOffice := Object clone
postOffice packageSender := method(call sender)             #return the sender

mailer := Object clone                                      #mailer object
mailer deliveryFrom := method(postOffice packageSender)     #Send the packageSender message to postOffice
mailer deliveryFrom                                         #mailer object
```

In the above example, invoking `deliveryFrom` on the `mailer` object sends a `message` to `postOffice`, which returns the `sender` - i.e. the `mailer` object itself.
Similarly, we can look at the target:

```Io
postOffice packageTarget := method(call target)             #return the target

mailer deliveryTo := method(postOffice packageTarget)   
mailer deliveryTo                                           #postOffice object
```

We can look at the arguments and the name as well:
```Io
postOffice packageInfo := method(call message arguments)
postOffice packageName := method(call message name)

postOffice packageInfo(216, "Belvoir Drive")    #list(216, "Belvoir Drive")
postOffice packageName                          #packageName
```
 
Io allows the receiver (or `target`) to evaluate the message (unlike, for instance java, which computes each value of a parameter and places that on the stack).
This is some pretty powerful stuff, meaning you can delay the execution, like in the following example.
```Io
unless := method(
    (call sender doMessage(call message argAt(0))) 
        ifFalse (call sender doMessage(call message argAt(1)))
        ifTrue (call sender doMessage(call message argAt(2)))
)

unless(1==2, "One is not two" println, "One is two" println) # ==> One is not two
```
In the above example, we use `doMessage` to execute an arbitrary message. 

### Reflection

Through a combination of `proto` and `slotNames` we can recursively examine the ancestors of an object and their methods.
But there isn't anything particularly surprising about doing that, so I'm going to ignore it. If you want a good look, have a mosey on over to page 66 of the book.

## Day three
### Domain-Specific Languages

`Io` allows the developer to manipulate the syntax, so imagine you wanted to represent phoneNumbers in the following format:
```text
{
  "Bob Anders": "07456123456",
  "Janet Anderson": "07123456123"
}
```

Instead of parsing the list, we can tell `Io` to interpret it.

```Io
OperatorTable addAssignOperator(":", "atPutNumber")
curlyBrackets := method(
    r := Map clone;
    call message arguments foreach(arg,
        r doMessage(arg)
        );
    r
)
Map atPutNumber := method(
    self atPut(
        call evalArgAt(0) asMutable removePrefix("\"") removeSuffix("\""),
        call evalArgAt(1)
        )
)

s := File with("phonebook.txt") openForReading contents
phoneNumbers := doString(s)
phoneNumbers keys println       # list(Janet Anderson, Bob Anders)
phoneNumbers values println     # list(07123456123, 07456123456)
``` 

Deconstructing the above:

First we register `:` in the `assignment operator table`. This means that whenever `:` is encountered, 
`Io` will parse that as `atPutNumber`, understanding that the first argument is a key (string) and the second
argument is a value. So `key : value` is parsed as `atPutNumber("key", value)`

Next up, the parser calls the `curlyBrackets` method whenever it encounters curly brackets (`{}`). Within this method,
we create an empty `Map`, and for each element in the curly brackets we send the message (in its `key : value` form)
to that `Map`.

The last thing we need to do is define the `atPutNumber` method that will be invoked whenever `Io` sees a `:`.
This method simply calls `atPut` with the arguments. However, remember that `key : value` is parsed as `atPutNumber("key", value)`.
Since our `key`s already have quotation marks, we need to strip these as we parse each `key`.

Now when we load in our phonebook file, we call `doString`, telling `Io` to evaluate the file as code, which returns a hash  of phone numbers.

### Method Missing / Building with `forward`

Similar to Ruby, `Io` will let you override the behaviour when invoking a method that does not exist.
For example, say you want to express the following `HTML` block:
```html
<body>
<p>
This is a simple paragraph.
</p>
</body>
```
... like this:
```Io
body(
    p("This is a simple paragraph.")
)
```

This can be done through `Io`s `forward` command.  Here's the code:
```Io
Builder := Object clone

Builder forward := method(
    writeln("<", call message name, ">");
    call message arguments foreach(
        arg,
        content := self doMessage(arg);
        if(content type == "Sequence", writeln(content)));
    writeln("</", call message name, ">"))

Builder ul(
    li("Io"),
    li("Lua"),
    li("JavaScript"))
```

This will generate:
```html
<ul>
<li>
Io
</li>
<li>
Lua
</li>
<li>
JavaScript
</li>
</ul>
```

### Concurrency
There are three areas of concurrency, `coroutines`, `actors` and `futures`

#### Coroutines

A `coroutine` provides a way to suspend and resume execution of a process. Think of it as a function with multiple entry
 and exit points. Each `yield` will voluntarily suspend the process, and transfer control to another process.
You can fire a message asynchronously by using `@` or `@@`. The latter immediately returns `nil` and starts the message in its own thread. 
 

```Io
vizzini := Object clone
vizzini talk := method(
    "Fezzik, are there rocks ahead?" println;
    yield;
    "No more rhymes now, I mean it." println;
    yield;)

fezzik := Object clone
fezzik rhyme := method(
    yield;
    "If there are, we'll all bbe dead." println;
    yield;
    "Anybody want a peanut?" println)

vizzini @@talk; fezzik @@rhyme

Coroutine currentCoroutine pause
```

In this block of code, we fire the `talk` and `rhyme` methods asynchronously, and then sned the `pause` message to the currently
executing coroutine at the end to wait until all of the async messages complete, before exiting. Without this final statement
the program will terminate before the messages complete.

The result is, as expected:

```text
Fezzik, are there rocks ahead?
If there are, we'll all bbe dead.
No more rhymes now, I mean it.
Anybody want a peanut?
Scheduler: nothing left to resume so we are exiting
```

#### Actors
`Actors` are responsible for changing their own state, and can only access other actors through closely controlled queues.
This holds certain advantages over `threads` which can change each other's state without restriction, making them vulnerable
to `race conditions`, where two `threads` access the same state at the same time. 

Sending an asynchronous message to any object makes it an `actor`

```Io
slower := Object clone
slower start := method(wait(2); writeln("slowly"))

faster := Object clone
faster start := method(wait(1); writeln("quickly"))
```

Running the code sequentially, as in the following:
```Io
slower start; faster start;
```
... results in the first message finishing before the second can begin, giving the output:

```text
slowly
quickly
```

However, we can make each object run its own thread with the `@@` symbol, as in the below code.

```Io
slower @@start; faster @@start; wait(3)
```
```text
quickly
slowly
```

Note we add a `wait` at the end. As in the `coroutines` example, this is to prevent the program from terminating before
the executing threads have completed. This `wait` can be replaced with the `Coroutine currentCoroutine pause` line from 
that example, if you don't know how long a timeout to specify.  The only difference that I can see is the presence of the
`Scheduler: nothing left to resume so we are exiting` output.

#### Futures

In `Io`, a `future` is a promise of a value, rather than the value itself. These are declared with the `@` symbol.
In the code below, the console will immediately write the line `wait here while we get the futureResult`, but the second
`writeln` statement will only be executed once the `futureResult` has been resolved.

```Io
slowFetch := method(wait(3); "got it")

futureResult :=  @slowFetch

writeln("wait here while we get the futureResult")
writeln("fetched ", futureResult size,  " bytes")
writeln("done")
```