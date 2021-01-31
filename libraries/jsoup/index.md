# Jsoup
Jsoup is a HTML Parser. The documentation can be found [here](https://github.com/jhy/jsoup)

I added methods like
- Jsoup.parseUrl(String) (static method) to instanciate a Document from an URL easily
- Element.getAt to access a i-th child `element[i]` (not sure it works, I haven't tested it)
- Element.propertyMissing(String name) a Groovy method invoked when accessing a property that doesn't exists.
This is used to access an Element's tag `element['src]`
