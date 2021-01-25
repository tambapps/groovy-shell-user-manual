# Android Groovy Shell  
Because programming on a smartphone is one of the most badass things to do in your life,  
I decided to adapt GroovySh to Android. This is an experimental app, not all Groovy feature are assured to work.
  
![screenshot 1](https://raw.githubusercontent.com/tambapps/android-groovy-shell/master/screenshots/screen1.jpg)

## Use cases

You can use this app to...

### Manage your files
````groovy
cd "Download"
f = file("text.txt")
temp = file("temp.txt")
touch temp
temp.text = f.text
temp << file("appendText.txt")
temp.remove()
````
This script is absolutely useless, but you get the idea of what you can do with Groovy Shell

### Perform network operations

You can use plain Java

```groovy
response = new URL("https://jsonplaceholder.typicode.com/").text
println response
```

or use my [http-client library](https://github.com/tambapps/java-rest-client)
```groovy
import http-client // this is an import alias, we'll get on that later
API_URL = "https://jsonplaceholder.typicode.com/"
client = new RestClient(API_URL)
request = Request.get("posts/1")
         .build()
response = client.execute(request, ResponseHandlers.json())
println response.data.title
```

or perform some web scrapping with [jsoup](https://github.com/jhy/jsoup)
```groovy
doc = Jsoup.connect("https://en.wikipedia.org/").get();
newsHeadlines = doc.select("#mp-itn b a");
for (Element headline in newsHeadlines) {
 println String.format("%s\n\t%s", headline.attr("title"), headline.absUrl("href"))
}
```

### Perform modify images directly on your phone
Using [Gmage](https://github.com/tambapps/gmage), you can load, modify and display images directly from the shell
```groovy
import gmage // import alias
gmage = Gmage.from('image.jpg') // actually this method doesn't exists in Gmage library. This method was dynamically added when processing the import alias
gmage.apply(ColorTransformers.duoTone(Color.BLUE))
gmage.apply { Color color -> color & 0xff00ff0f }
gmage.apply(ColorTransformers.replaceColor(Color.BLUE, Color.CLEAR, 0.25f))
def blurred = gmage.blurred(new BoxBlur(BoxBlur.SHARPENING_KERNEL))
def resized = blurred.scaledBy(0.8f, 0.8f)
resized.show() // display the gmage
GmageEncoder.encode(resized, CompressFormat.PNG, new File("output.jpg"))
```

### Create and display charts
I created a wrapper library of [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)
that allows you to create and display charts.

```groovy
import chart // more import aliases
import time
chart = new LineChart()
x = []
for (i in (1..10)) x.add(LocalDateTime.now().minusDays(10 - i) as Long)
r = new Random()
y = []
for (i in (1..10)) y.add(r.nextInt(100))
chart.setX(x)
  .setY(y)
  .setTimeUnitX(SECONDS)
  .show()
```

### Process json/xml data

```groovy
import json
def jsonSlurper = new JsonSlurper()
def object = jsonSlurper.parseText('{ "name": "John Doe" }')
println object.name
```

```groovy
def text = '''
    <list>
        <technology>
            <name>Groovy</name>
        </technology>
    </list>
'''

def list = new XmlParser().parseText(text) 

println list.technology.name.text() == 'Groovy' 
```

### Schedule a script to run
I introduced the notion of Shell Works, a script that runs directly on your device. You can also
schedule it to run later, and/or make it periodic. For example, you can write a script to check if
there are some new articles on your favorite website and notify you about!

Here is an example of script

```groovy
import bitmap

// you can load 'Environment' that you defined earlier
// an environment is basically a script. Loading it is executing it
// It useful to store some functions that you would often use. In this
// case, the environment "Website Check" defines all the functions used below
loadEnvironment "Website Check"

// variable accessible from shell work
if (!isNetworkAvailable) {
  return "Network not available"
}
int retryCount = 3
for (i in 1..retryCount) {
  try {
    newEpisodes = getNewEpisodes()
    break
  } catch (Exception e) {
    if (i == retryCount) {
      throw e
    }
  }
}

if (!newEpisodes) {
  return "no new episodes\n"
}
String title = null
String message = ""
Bitmap icon = null
try  {
 // static function added with import alias
  icon = Bitmap.fromURL(newEpisodes[0].imageUrl)
} catch(Exception e) {}

if (newEpisodes.size() == 1) {
  def e = newEpisodes[0]
  title = "${e.title} is out!"
} else {
  setNotificationBigText(true)
  title = "Many episodes are out!"
  message = "- " + newEpisodes*.title.join("\n- ")
}

// set the notification that will be displayed at the end
// of this shell work
setFinalTitle title
setFinalMessage message
setIcon icon

// save new last episodes in tracks
flushNewEpisodes newEpisodes

// optionally return a result for the work
return newEpisodes.size() + " new episodes"
```

## Features 
Now let's talk about technical features handled
You can use groovy syntax features, write functions, call them, define classes instantiate objects...
You can also use Java 8 classes such as LocalDateTime, Instant and even Streams (maybe that depends on your Android device).
Groovy syntax is compatible with Java syntax, but note that it doesn't supports Java lambda expression
syntax. Use closure instead (e.g `{ Object o -> println(o) }`).

### What does NOT work
Unfortunately, Grape, the dynamic dependency manager does not work

### Variable declarations
 Be careful with variable declaration. If you put the type (or `def`) before the variable, you submit the code and then try to access the same variable, it won't exist anymore. If you want to keep the variable after you submitted your code you'll have to omit the type/`def` like in the following example:

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

In addition of Groovy default imports, here is the list of package imported by default in this shell
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

#### Import Aliases
There are some aliases to import multiple packages, here is the list

- `http-client` to import packages from my [Http Client Library](https://github.com/tambapps/java-rest-client)
- `gmage` to import packages from my [Image Processing library](https://github.com/tambapps/gmage)
- `jsoup` to import packages from [Jsoup](https://jsoup.org/)
- `time-unit` to import enums from java TimeUit class
- `json` to import packages from `groovy.json`
- `xml` to import packages from `groovy.xml`
- `chart` to import classe from the chart library, wrapper of [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)
  TODO make some documentation about it
- `bitmap` to import classes from Android SDK Bitmap 

To use one, simply import it like you would import any package:
````groovy
import rest-client
````

Each import script has a script that adds some functions in some objects

TODO document them


### Class definition
You can define classes. When you do so, you can't put any other instructions after in the same prompt (yeah, this is weird but this is how it works).
You CAN put other instructions only if between the class definition and the rest, you put the comment`/*PROMPT*/`.
 To get instances from the defined class, you'll need to use java.reflect methods. You can't construct instances with 
the syntax `a = new A()`.
 All defined classes will appear in the variable `CLASSES`, a map of cass name to java Class.


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

Defining multiple classe and doing things after (in a same prompt)

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

doSomething(a, b)
```
### Give permission  
If some network or file operations don't work, it may be because you didn't give permissions for the app to perform them

Here is the list of permissions you can ask (each i)
- WRITE_EXTERNAL_STORAGE
- READ_EXTERNAL_STORAGE
- ACCESS_NETWORK_STATE
- INTERNET

#### Ask permission 
```groovy  
permission = Permissions.WRITE_EXTERNAL_STORAGE
requestPermission(permission)
```

If you want to ask all permissions, you can enter
```groovy  
requestPermission(*PERMISSIONS)
```
Groovy spread operator will spread all permissions into function arguments

### Files  
You can manipulate files of your local device. There is a notion of current directory like in shell. You can find the functions
`cd()`,  `ls()` `pwd()`.
There is the function `file(String)` allowing to get a file
relatively from the current directory.
Since Groovy is a scripting language, you can omit parenthesis. By doing so you can write shell-like code like in the following example

```groovy  
pwd
cd 'folder'
ls 
cd '..'
```
Normally you can't omit parenthesis for a function with 0 arguments but I  added a little tweak so you can

## Functions added
Thanks to Groovy meta programming, I added some new methods to several classes

#### File
- setLastModifiedTime(Long|Date|LocalDateTime)
- setCreationTime(Long|Date|LocalDateTime)
- setLastAccessTime(Long|Date|LocalDateTime)
- getLastModifiedTime() -> Long
- getCreationTime() -> Long
- getLastAccessTime() -> Long

#### Date
- toInstant() -> Instant

#### LocalDateTime
- toDate() -> Date

#### LocalDate
- toDate() -> Date

#### String
- toLocalDateTime() -> LocalDateTime
- toLocalDate() -> LocalDate

### Network
You can use the `URL` class, like you would do in Java.

### Http/Rest Client
I added static methods to handle JSON/XML request with Groovy
- BodyProcessors.json(Object) -> Returns a BodyProcessor converting the object into json
- ResponseHandlers.json() -> Returns a ResponseHandler that uses a `JsonSlurper` to parse the request's data
- ResponseHandlers.xml() -> Returns a ResponseHandler that uses a `XmlSlurper` to parse the request's data

### Bitmap
You can use android Bitmap class to manipulate images
```groovy
import bitmap
f = file("image.png")
b = f.toBitmap()
b = b.scaled(105,105)
// default format is PNG
b.writeInto(file("result.png"))
b.writeInto(file("result.png"), JPEG)
```
#### Example:
```groovy
import rest-client // import alias to import all usefull class from com.tambapps.http.restclient

url = "https://jsonplaceholder.typicode.com/"
client = new RestClient(url)
request = RestRequest.builder("/todos/1")
.GET()
.build()
response = client.execute(request, ResponseHandlers.json())
todo = response.data
println("${todo.title} is ${todo.completed}")
```

### Charts
The library MPAndroidChart was used to display line charts.
For that you'll need to import the alias `chart`
```groovy
import chart
```

There are (for now) two kinds of charts
- LineChart: a line chart
- MultiLineChart : a chart allowing to display multiple lines

Each of these chart objects have a method `show()` allowing to show the chart.
By long clicking it, you can save it into a PNG file

### STDIN
You can also write programs using the standard input with the help of the `System.console()` (or variable `console`)

```groovy
input = System.console().readLine 'Enter some input'
```

In the above example, the String passed in parameter will be displayed in the standard output (in the shell), then
You'll have to enter some input. The text color should be blue when entering some input in STDIN. Click the prompt button
when finished
## User Interface

### Menu
There is a menu where you can change screen


### Shell
This is where you prompt commands/code

### Variables and functions
This is where you can see all variables and functions declared
You swipe left or right to switch from variables to functions, from functions to all

### Load script
Allow to load groovy file

### Save to file
Allow to save all commands entered to a file

### Handle Shell works
Shell works are Groovy script that are executed in the background, even when this app is closed.
You can also schedule them and/or make them recursive

### Change keyboard
Useful if you have downloaded the Codeboard (android keyboard for coding) 


## Libraries

This project uses several libraries:
- httpclient-core (kotlin branch)
- httpclient-okhttp (kotlin branch)
- gmage-core
- gmage-android