# Changelog

## 3.0.0

- [Breaking Change] defined classes can now be used directly (`new A()` instead of `CLASSES.A.newInstance()` before)

### Introducing the dependency manager GrapeDex and Dexter
- Can import dependencies dynamically from maven repository see doc [here](TODO)
- Can import dependencies dynamically from local jars or url, with Dexter see doc [here](TODO)
- Can handle dependencies loaded dynamically [here](TODO)


### Other changes 
- better handling of imports
- renamed respectively import aliases `http-client` and `time-unit` to `httpclient` and `timeunit`
- added `cat(Object)` method
- added groovy-templates module
- fixed multithreading problem when loading environment/file


## 2.3.0
- fixed bug when loading environment in a script
- added groovy-templates submodule


## 2.2.0

- added `network required` in Shell Work form
- remove variable `isNetworkAvailable` previously available from shell work script
- made order of `ls()` files configurable in preferences
- performance improvment on code highlight
- improved Text Prompt Helper
- Added environment directory `envDir` variable when loading an environment

 
## 2.1.1

- Made documentation accessible directly from app
