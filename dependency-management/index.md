# Dependency Management

The Android Groovy Shell has a dependency manager that allows you to
quickly add maven repository dependencies to your classpath.

## Load Maven dependencies
You can load dependencies directly from Maven (more details [here](https://tambapps.github.io/groovy-shell-user-manual/dependency-management/dexgrape/))
```groovy
import dex
DexGrape.grab("com.google.code.gson", "gson", "2.8.6")
import com.google.gson.Gson
g = new Gson()
```


## Load local jars
Or from a local jar (more details [here](https://tambapps.github.io/groovy-shell-user-manual/dependency-management/dexter/))
```groovy
f = file("groovy/fandem.jar")
// import alias
import dex
// this will add the jar to your class path
Dexter.loadFromFile(f)
// you can then use classes from the jar
import com.tambapps.p2p.fandem.FileSharer
f = new FileSharer()
```

## Load additional jars every time you starts a shell

You can add jars to your classpath permanently by adding them to your [addittional classpath](https://tambapps.github.io/groovy-shell-user-manual/dependency-management/additional-classpath/)