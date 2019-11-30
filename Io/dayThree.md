# Day Three

## Do:

### One & Three

Enhance the XML program to add spaces to show the indentation structure.
Enhance the XML program to handle attributes: if the first argument is a map (use the curly brackets syntax), add 
attributes to the XML program. For example, `book({"author": "Tate"}...)` would print `<book author="Tate>...</book>`

#### Code
```Io
OperatorTable addAssignOperator(":", "atPutAttribute")

curlyBrackets := method(
    call message arguments map(arg, self doMessage(arg)) join("")
)

atPutAttribute := method (
    key := call evalArgAt(0) asMutable removePrefix("\"") removeSuffix("\"")
    value := call evalArgAt(1)
    " #{key}=\"#{value}\"" interpolate
)


Builder := Object clone do (
    indent := ""

    forward := method(
        arguments := call message arguments
        name := call message name
        attributes := ""
        if(arguments at(0) name == "curlyBrackets", attributes = doMessage(arguments removeFirst))

        writeln(indent, "<", name, attributes, ">");
        arguments foreach(arg,
            indent = indent .. "  ";
            content := self doMessage(arg);
            if(content type == "Sequence", writeln(indent, content))
            indent = indent exclusiveSlice(2)
        )
        writeln(indent, "</", name, ">")
    )
)

doFile("test.io")
```

#### Test file
This must be in a separate file, for the `OperatorTable` to contain the added `atPutAttribute` definition 
```Io
Builder library(
    book({"author" : "Tate", "isbn" : "012345678"}, title("Seven Languages in Seven Weeks")),
    book({"author" : "Karwin"}, title("SQL Antipatterns"))
    )
```

#### Output
```xml
<library>
  <book author="Tate" isbn="012345678">
    <title>
      Seven Languages in Seven Weeks
    </title>
  </book>
  <book author="Karwin">
    <title>
      SQL Antipatterns
    </title>
  </book>
</library>

```

### Two
Create a list syntax that uses [square] brackets

```Io
squareBrackets := method(
  call message arguments
)
 
[1, 2, "a", 10] println     # list(1, 2, "a", 10)
```
This is rather simple - the arguments to the square brackets is already a list, which we can simply extract