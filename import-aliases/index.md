# Import Aliases
An import alias is basically an alias that will be used to import many packages (and eventually enums),
in one commands. Here is the list of import aliases

- `hyperpoet` to import packages from my [Http Client Library](https://github.com/tambapps/java-rest-client)
- `gmage` to import packages from my [Image Processing library](https://github.com/tambapps/gmage)
- `jsoup` to import packages from [Jsoup](https://jsoup.org/)
- `timeunit` to import enums from java TimeUit class
- `time` to import dates Java classes
- `json` to import packages from `groovy.json`
- `xml` to import packages from `groovy.xml`
- `chart` to import classes from the [chart library](https://tambapps.github.io/groovy-shell-user-manual/libraries/charts/), wrapper of [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)
- `bitmap` to import classes from Android SDK Bitmap

To use one, simply import it like you would import any package:
````groovy
import gmage
````

An import alias can have a script that runs when imported to (for example) add some functions in some objects, by using
Groovy meta-programming


Here are some example of codes with added functions for some import aliases

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
import hyperpoet
API_URL = "https://jsonplaceholder.typicode.com/"
client = new HttpPoet(url: API_URL, contentType: ContentType.JSON, acceptContentType: ContentType.JSON)
data = client.get("/posts")
println data.title
```

