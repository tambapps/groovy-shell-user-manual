# Managing Files
You can manipulate files of your smartphone. 
There is a notion of current directory like in an actual desktop shell.
The current directory is stored in the variable `pwd`, along with its `parentFile`. 
You can instantiate files relatively from the current directory with
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



## Shell-like file functions

There are several methods allowing you to manage your files. Each of these functions takes an Object
as argument which can be of the following types:
- String -> It will convert it to a file relative to `pwd` (like calling `file(string)`) or an absolute file
  if the string starts with `/`
- Path -> It will simply convert the Path to a File
- File -> no conversion needed

### cd(Object)
Allows you to Change Directory. It will update `pwd` and `parentFile` variables

### ls()
It will list all file names (directories included) in the current directory `pwd`

### lsFiles()
It will list all files (directories included) in the current directory `pwd`

### lsPattern(String)
It will list all the file names (directories included) matching the given [glob pattern](https://en.wikipedia.org/wiki/Glob_(programming))

Example
```groovy
lsPattern("*.groovy")
```

### lsFilesPattern(String)
It will list all the files (directories included) matching the given [glob pattern](https://en.wikipedia.org/wiki/Glob_(programming))

### mkdir(Object)
Will create a directory if non-existing

### touch(Object)
Will update the `Last Upated` time of a given file. If the file doesn't exists, an empty file will
be created

### cat(Object)
It will display the content of a text file

### cp(Object, Object)
It will copy/override the first given file/directory on the second argument (file/directory) 

### mv(Object, Object)
Used to rename files/directories