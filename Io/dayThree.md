# Day Three

## Do:

### One 

Enhance the XML program to add spaces to show the indentation structure.

```Io
Builder := Object clone
Builder indent := ""

Builder forward := method(
    arguments := call message arguments
    name := call message name

    writeln(indent, "<", name, ">");
    arguments foreach(arg,
        indent = indent .. "  ";
        content := self doMessage(arg);
        if(content type == "Sequence", writeln(indent, content))
        indent = indent exclusiveSlice(2)
    )
    writeln(indent, "</", name, ">"))

Builder body(ul(
    li("Io"),
    li("Lua"),
    li("JavaScript")))
```

```html
<body>
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
</body>
```