# Android Groovy Shell  
Because programming on a smartphone is one of the most badass things to do in your life,  
I decided to adapt GroovySh to Android. This is an experimental app, not all Groovy feature are assured to work.
  
![screenshot 1](https://raw.githubusercontent.com/tambapps/android-groovy-shell/master/screenshots/screen1.jpg)

## Glossary

* Shell Features
  * [Shell (general)](https://tambapps.github.io/groovy-shell-user-manual/shell-general/)
  * [Shell (managing files)](https://tambapps.github.io/groovy-shell-user-manual/shell-managing-files/)
  * [Import Aliases](https://tambapps.github.io/groovy-shell-user-manual/import-aliases/)
  * [User Interface](https://tambapps.github.io/groovy-shell-user-manual/user-interface/)
  * [Shell Works](https://tambapps.github.io/groovy-shell-user-manual/shell-works/)
* Libraries
  * [bitmap](https://tambapps.github.io/groovy-shell-user-manual/libraries/bitmap/)
  * [charts](https://tambapps.github.io/groovy-shell-user-manual/libraries/charts/)
  * [gmage](https://tambapps.github.io/groovy-shell-user-manual/libraries/gmage/)
  * [http-client](https://tambapps.github.io/groovy-shell-user-manual/libraries/http-client/)
  * [jsoup](https://tambapps.github.io/groovy-shell-user-manual/libraries/jsoup/)

## Use cases
This is a quick presentation of the Groovy Shell. If you want more details on a section,
click on the corresponding link in the glossary.

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
I introduced the notion of Shell Works, a script that runs on the background. You can also
schedule it to run later, and/or make it periodic. For example, you can write a script to check if
there are some new articles on your favorite website and notify you about!

Here is an example of script

```groovy
import bitmap

// you can load 'environments' that you defined earlier in the app
// an environment is basically just a script. Loading it means executing it
// Environments are useful to store some functions that you would often use. In this
// case, the environment "Website Check" defines all the functions used below
loadEnvironment "Website Check"

// variable accessible from shell work
if (!isNetworkAvailable) {
  return "Network not available"
}
int retryCount = 3
for (i in 1..retryCount) {
  try {
    // function from environment Website Check
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
  // shell work function to make the Android notification test bigger
  setNotificationBigText(true)
  title = "Many episodes are out!"
  message = "- " + newEpisodes*.title.join("\n- ")
}

// shell work functions to set the notification that will be displayed at the end
// of this shell work
setFinalTitle title
setFinalMessage message
setIcon icon

// save new last episodes in tracks
flushNewEpisodes newEpisodes

// optionally return a result for the work
return newEpisodes.size() + " new episodes"
```





### Give permission  










## Miscellaneous

