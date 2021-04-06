# Import Aliases
An import alias is basically an alias that will be used to import many packages (and eventually enums),
in one commands. Here is the list of import aliases

- `httpclient` to import packages from my [Http Client Library](https://github.com/tambapps/java-rest-client)
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
import httpclient
````

Each import alias has a script that runs and adds some functions in some objects, by using
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
import httpclient

url = "https://jsonplaceholder.typicode.com/"
client = new RestClient(url)
request = RestRequest.builder("/todos/1")
.GET()
.build()
response = client.execute(request, ResponseHandlers.json())
todo = response.data
println("${todo.title} is ${todo.completed}")
```

