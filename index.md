# Android Groovy Shell  
Because programming on a smartphone is one of the most badass things to do in your life,  
I decided to adapt [GroovySh](https://groovy-lang.org/groovysh.html) to Android. 


This app allows you to write, compile and run Groovy code directly from your smartphone. It also works offline

<a href='https://play.google.com/store/apps/details?id=com.tambapps.android.grooidshell&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png' width="200"/></a>  

I strongly recommend using this app with a keyboard adapted for coding such as [CodeBoard](https://play.google.com/store/apps/details?id=com.gazlaws.codeboard)

## Glossary

* Shell Features
  * [Shell (general)](https://tambapps.github.io/groovy-shell-user-manual/shell-general/)
  * [Shell (managing files)](https://tambapps.github.io/groovy-shell-user-manual/managing-files/)
  * [Import Aliases](https://tambapps.github.io/groovy-shell-user-manual/import-aliases/)
  * [Dependency Management](https://tambapps.github.io/groovy-shell-user-manual/dependency-management/)
  * [Environments](https://tambapps.github.io/groovy-shell-user-manual/environments/)
  * [Shell Works](https://tambapps.github.io/groovy-shell-user-manual/shell-works/)
  * [Auto Completion](https://tambapps.github.io/groovy-shell-user-manual/auto-completion/)
* Libraries
  * [hyperpoet](https://github.com/tambapps/hyperpoet/wiki)
  * [grooview](https://github.com/tambapps/grooview)
  * [charts](https://tambapps.github.io/groovy-shell-user-manual/libraries/charts/)
  * [gmage](https://tambapps.github.io/groovy-shell-user-manual/libraries/gmage/)
  * [jsoup (not from me)](https://tambapps.github.io/groovy-shell-user-manual/libraries/jsoup/)
* [Changelog](https://tambapps.github.io/groovy-shell-user-manual/changelog/)

## Use cases
This is a quick presentation of the Groovy Shell. If you want more details on a section,
click on the corresponding link in the glossary.

You can use this app to...

### Write Java/Groovy programs

```groovy
fibonacci = { Integer n ->
  switch (n) {
    case 0:
    case 1:
      return n
    default:
      return fibonacci(n - 1) + fibonacci(n - 2)
  }
}

fibonacci(10)
```

### Import and use your favorite Java libraries

With DexGrape, you can import dependencies from a Maven repository:

```groovy
import dex
DexGrape.grab('org.apache.commons', 'commons-csv', '1.8')
import org.apache.commons.csv.CSVFormat

f = file("biostats.csv")
Reader reader = f.newReader()
def records = CSVFormat.DEFAULT.withHeader(["Name", "Gender", "Age", "Height", "Weight"] as String[])
        .withFirstRecordAsHeader()
        .parse(reader)
for (record in records) {
  String name = record.get("Name")
  String age = record.get("Age")
  println "$name, $age"
}
reader.close()
```

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

use my [http-client library](https://github.com/tambapps/hyperpoet)

```groovy
import hyperpoet // this is an import alias, we'll get on that later
API_URL = "https://jsonplaceholder.typicode.com/"
client = new HttpPoet(url: API_URL, contentType: ContentType.JSON, acceptContentType: ContentType.JSON)
data = client.get("/posts")
println data.title
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
I introduced the notion of Shell Works, a script that runs in the background. You can also
schedule it to run later, and/or make it periodic. For example, you can write a script to check every day if
there are some new articles on your favorite website and notify you about!

You can learn more about it [here](https://tambapps.github.io/groovy-shell-user-manual/shell-works/)
