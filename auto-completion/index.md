# Auto Completion (pro feature)
Auto completion (or AC) is a useful tool helping you find fields/functions from
objects, or from the shell. It is triggered with a left bottom button. This button will show
a dialog with all possible completions. variable types and function signatures
will also be displayed

Auto completion will only try to complete the end of your text. Even if your cursor is in the middle,
it will try to look for completion only at the end of your prompt text.

## Completing shell variables
The AC can propose you shell variables/functions. Actually shell functions are 
[Closures](https://docs.groovy-lang.org/latest/html/api/groovy/lang/Closure.html), 
so they are variables too.

e.g:

```groovy
CLAS
```
Using AC with this input will propose you the variables `CLASSES`

```groovy
load
```
Using AC with this input will propose you the functions `loadEnvironment` and `loadScriptFile`

## Completing fields/functions from objects
The AC helps you find fields/functions from objects. 
```groovy
pwd.ex
```
Using AC with this input will propose you the `File.exists()` method, and the `File.executable` Groovy field 
(coming from the Java function `File.isExecutable()`).

It will also propose you functions from [Groovy extension modules](https://groovy-lang.org/metaprogramming.html#_extension_modules).
For example, for Collections it can propose you functions like `collect(Closure)` or `findAll(Closure)`
and many others.

```groovy
pwd.eachFile
```
Using AC with this input will propose you GDK File functions like `eachFile(Closure)`, 
`eachFileRecurse(Closure)`, `eachFileMatch(Object, Closure)`


## Completing files
AC can help you find files. for example, with the following input

```groovy
cd "Do"
```
AC will find all directories starting with Do and then propose them to you in the completions dialog.

You can also be proposed the list of all directories (from the current directory, AKA `pwd`) with the following input
inputs
```groovy
cd ""
```
or
```groovy
cd 
```
The same goes for the `file(Object)` function that wil propose files from `pwd`

The completion works recursively for directories within directories:
```groovy
cd "Download/"
```
With this input, AC will propose you all subdirectories of `Download` directory


## Completing environments
It can also help you complete environments when you want to load them.

E.g:
```groovy
loadEnvironment "Env"
```
Applying AC with this input will propose all environments starting with `Env`

You can also be proposed all environments by applying the same logic as seen above:

```groovy
loadEnvironment ""
```
or
```groovy
loadEnvironment 
```

## Completing permissions
It can also help you complete permissions when requesting them.

E.g:
```groovy
requestPermission "WRITE"
```
Applying AC with this input will propose the permission `WRITE_EXTERNAL_STORAGE`

You can also be proposed all permissions by applying the same logic as seen above.

## Screenshot example

![Auto-completion screenshot](https://tambapps.github.io/groovy-shell-user-manual/images/auto-completion.jpg "Auto-completion screenshot")


You can see the completion dialog presenting possible completions.

In the bottom left of the screen, there is the AC button