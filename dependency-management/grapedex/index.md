# GrapeDex the Maven dependency manager

Since Groovy Shell 3.0.0, you can now load dependencies dynamically from Maven. It is the adaptation of Groovy [Grape](http://docs.groovy-lang.org/latest/html/documentation/grape.html).

Note that jars loaded by `GrapeDex` will be added to your classpath only for the current session. When
the app is killed, all loaded dependencies are discarded. If you want to keep dependencies on your classpath
everytime you start a Shell, you can achieve that by [managing your additional class path](https://tambapps.github.io/groovy-shell-user-manual/dependency-management/additional-classpath/)

## How it works

Grape will fetch dependency from Maven (it will also resolve and fetch transitive dependencies) and then save them into the local repository of the Groovy Shell.

Since Android uses [Dalvik byte code](https://source.android.com/devices/tech/dalvik/dalvik-bytecode), it will then convert jars
into dex jars (jar of Dalvik byte code classes) using [Dexter](https://tambapps.github.io/groovy-shell-user-manual/dependency-management/dexter/). Only converted jars will be kept
(since Android can't load original jars it is useless to keep them).

If you want to take a look on how dependencies are resolved, you can consult the [Maven Dependency Resolver](https://github.com/tambapps/maven-dependency-resolver).

## How to use it

Start by using the import alias `dex` for simplicity

```groovy
import dex
```

### Load Maven dependency
Just grab the dependency with `DexGrape`
```groovy
import dex
addedJarsToClassPaths = DexGrape.grab("com.google.code.gson", "gson", "2.8.6")
println "List of jars added to classpath: $addedJarsToClassPaths"

import com.google.gson.Gson
g = new Gson()
```

It also works with plain Groovy `Grape.grab()`
```groovy
groovy.grape.Grape.grab(group:'com.google.code.gson', module: 'gson', version: '2.8.6')
```

### Resolve Maven dependency
Resolving a Maven will just fetch the dependency (and its transitive), and return them.


```groovy
resolvedArtifacts = DexGrape.resolve("com.google.code.gson", "gson", "2.8.6")
```

### List fetched artifacts
You can list fetched artifacts, jars from your local repository as follows:
It returns a map String -> (String -> (Artifact)). It is because it maps
groupId -> artifactId -> list of all versions of this artifact
```groovy
artifacts = DexGrape.listFetchedArtifacts()

println('All fetched gson artifacts ' + artifacts['com.google.code.gson']['gson'])
```

### Handle fetched artifacts

You can handle artifacts from your local repository through the `Preferences` screen, at the item
sdf `Dex jars repository`. There, you can delete fetched jars or add some to your 
[additional class path](https://tambapps.github.io/groovy-shell-user-manual/dependency-management/additional-classpath/)