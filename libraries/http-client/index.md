# Http/Rest Client
I added static methods to handle JSON/XML request with Groovy
- BodyProcessors.json(Object) -> Returns a BodyProcessor converting the object into json
- ResponseHandlers.json() -> Returns a ResponseHandler that uses a `JsonSlurper` to parse the request's data
- ResponseHandlers.xml() -> Returns a ResponseHandler that uses a `XmlSlurper` to parse the request's data

You can find a documentation of the library [here](https://github.com/tambapps/java-rest-client)