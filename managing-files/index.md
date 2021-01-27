# Managing Files
You can manipulate files of your smartphone. 
There is a notion of current directory like in an actual desktop shell. 
You can find the functions `cd()`,  `ls()` `cp()`. 

The current directory is stored in the variable `pwd`, and its
parent in `parentFile`. You can instantiate files relatively from the current directory with
the function `file(String)` allowing to get a file.
relatively from the current directory.
Since Groovy is a scripting language, you can omit a parenthesis. 
By doing so you can write shell-like code as shown in the below example

```groovy  

cd 'Download'
f = file('test.txt')
cp f, 'test2.txt' 
cd '..'
```

Functions like `cd()`,  `ls()` `cp()` take Object(s) in parameter and will do their best to convert
the parameter(s) into a File

If the parameter is a
- String -> It will convert it to a file relative to `pwd` (like calling `file(string)`) or an absolute file
if the string starts with '/'
- Path -> It will simply convert the Path to a File
- File -> no conversion needed