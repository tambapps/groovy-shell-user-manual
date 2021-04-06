# Changelog

## 3.0.0

- [Major Improvment] defined classes can now be used directly (`new A()` instead of `CLASSES.A.newInstance()` before)
- [New Feature] Can import dependencies dynamically from maven repository (consult doc [here](https://tambapps.github.io/groovy-shell-user-manual/dependency-management/dexgrape/))
- [New Feature] Can import dependencies dynamically from local jars or url (consult doc [here](https://tambapps.github.io/groovy-shell-user-manual/dependency-management/dexter/))
- better handling of imports commands
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
