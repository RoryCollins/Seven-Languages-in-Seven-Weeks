# Scala
## Day One 
### Introduction
Scala supports both object oriented and functional programming, so developers can gradually learn to apply functional 
programming ideas to their code. 

Scala offers tight integration to Java, not only does it run on the JVM, allowing it to run side by side with java code
and libraries through interoperability, it also uses a relatively close syntax, so developers can learn the basics quickly.

Scala is strongly and statically typed.

```Scala
4 + "1.0"
// java.lang.String = 41.0
```
The does not throw an error, because scala is able to coerce the integer into a string, and use concatenation.

```Scala
4 * "1.0"
// error: ...
```
In the above example, we are better able to force a mismatch

*strong typing* is where the language detects when two types are incompatible, either throwing an error or coercing the types
if they are not.
 
*static typing* is where the structure of an object is enforced by its type.

### Syntax: Loops
#### While
`While` is pretty bog standard. 
```Scala
def whileLoop {
  var i = 1
  while(i <= 3) {
    println(i)
    i += 1
  }
}
```
The most interesting points here are that you don't have to specify the access modifier, which is `public` by default.
Additionally, `var` is used to specify a `mutable` variable. Immutable variables are specified by `val`.

#### For
`for` is slightly different:
```Scala
def forLoop { 
  for(i<-0 until 3){
    println(i)
  }
}
```
here, the `for` loop takes a `Range` as an argument.
