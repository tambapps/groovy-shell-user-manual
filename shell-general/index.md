# Shell (general)
This section talks about some Groovy features handled by this shell and how to use them.

## Features
This shell can use most of Groovy SDK. It is based on Groovy 2.4.XX. The version used will be
displayed when you'll start the shell.
You can use groovy syntax features, write functions, call them, define classes instantiate objects...
You can also use Java 8 classes such as LocalDateTime, Instant and even Streams (maybe that depends on your Android device).
Groovy syntax is compatible with Java syntax, but note that it doesn't support Java lambda expression
syntax. Use closure instead (e.g `{ Object o -> println(o) }`).


Groovy meta-programming also works. You can define, override method of classes/objects at runtime.
To perform that, check the [Groovy documentation](https://groovy-lang.org/documentation.html)

### What does NOT work
Unfortunately, Grape, the dynamic dependency manager does not work.
Now let's get back on what does work

### Variable declarations
Be careful with variable declaration. If you put the type (or `def`) before the variable, you submit the code (a prompt) and then try to access the same variable, it won't exist anymore.
If you want to keep the variable after you submitted your code you'll have to omit the type/`def`.

The following example will not work
images
prompts
```groovy
def a = 2
String variable = "some value"
```
then prompts
```groovy
// will throw an exception because shell doesn't know 'a' and 'variable' anymore
a == variable
```

Here is the proper way to declare variables if you want to keep them in your next prompts

```groovy
a = 2
variable = "some value"
```
### Imports
Here is an example of import
Example:
````groovy
import java.nio.file.Path
````

In addition, of [Groovy default imports](https://groovy-lang.org/structure.html#_default_imports), here is the list of package imported by default in this shell
```text
java.time.*
java.time.format.*
java.util.concurrent.*
groovy.json.*
groovy.xml.*
java.nio.file.*
java.util.zip.*
java.util.regex.*
java.nio.file.*
java.util.concurrent.atomic.*
```

Furthermore, you can import many packages (and enums) with [import aliases](https://tambapps.github.io/groovy-shell-user-manual/import-aliases/)

### Ask permission
Android restricts the app access to files. To grant it, you'll need to ask permission like in the following
example.
```groovy  
permission = Permissions.WRITE_EXTERNAL_STORAGE
requestPermission(permission)
```

If you want to ask all permissions, you can enter
```groovy  
requestPermission(*PERMISSIONS)
```

Here is the list of permissions you can ask
- WRITE_EXTERNAL_STORAGE
- READ_EXTERNAL_STORAGE
- ACCESS_NETWORK_STATE

You can also see granted permissions in the preferences screen.

### Class definition
You can define classes. When you do so, by default, you can't put any other instructions after in the same prompt, they won't be processed (yeah, this is weird but this is how it works).
You CAN put other instructions only if between the class definition and the rest, you put special the comment`/*PROMPT*/`.
To get instances from the defined class, you'll need to use java.reflect methods. You can't construct instances with
the syntax `a = new A()`.
All defined classes will appear in the variable `CLASSES`, a map of class name -> class.


e.g:

Class definition
```groovy
class A {
 int b
}
```


Getting instances
```groovy
a = CLASSES.A.newInstance()
a2 = CLASSES.A.newInstance(b: 1)
```

Defining multiple classes and doing things after (in a same prompt)

```groovy
class A {
 int b
}
/*PROMPT*/
class B {
 int a
}
/*PROMPT*/
a = CLASSES.A.newInstance()
b = CLASSES.B.newInstance()
/*PROMPT*/
doSomething(a, b)
```

### Standard Input (STDIN)
You can also write programs using the standard input with the help of the `System.console()` (or variable `console`)

```groovy
input = System.console().readLine 'Enter some input'
```

In the above example, the String passed in a parameter will be displayed in the standard output (in the shell, like a `println` would), then
You'll have to enter some input. The text color should be blue when entering some input in STDIN. Click the prompt button
when finished, to submit the text to STDIN.

So you can write scripts that takes input data.
