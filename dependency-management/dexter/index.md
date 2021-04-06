# Dexter, the jar to dex jar handler

Dexter allows you to load jars and add them to your classpath so that you can use the classes held
in them.


## How it works
Android uses [Dalvik byte code](https://source.android.com/devices/tech/dalvik/dalvik-bytecode), it doesn't
work with normal jars. 
Before loading a normal jar, it has to be converted into a dex jar (a jar holding Dalvik byte code).
This is the role of Dexter: it converts the jar into a dex jar (if needed) and then adds it to your 
classpath using the [ClassPathHandler](https://tambapps.github.io/groovy-shell-user-manual/dependency-management/additional-classpath/).

## How to use

Start by using the import alias `dex` for simplicity

```groovy
import dex
```

### Load a jar

There are many ways to load a jar. If you don't want to worry about the technical stuff, or don't know
if your jar is a dex jar or a normal one, just use the `Dexter.loadFromFile()`
```groovy
Dexter.loadFromFile(file("groovy/fandem.jar"))
// you can then use classes from the jar
import com.tambapps.p2p.fandem.FileSharer
f = new FileSharer()
```

If you know what type of jar you're dealing with, you can use the methods `Dexter.loadFromDexFile()` or
`Dexter.loadFromJarFile()` to respectively load a normal jar or dex jar.

To help you recognize dex jar, there is the method `Dexter.isDexJar(File)`

You can also load a jar from an URL
```groovy
Dexter.loadFromURL(new URL("https://some-url.com/to/a/library.jar"))
```

### Convert a normal jar to a dex jar
You can convert a normal jar to a dex jar by providing an input file, the normal jar to be converted,
and an output file, the destination file. The input and output file have to be different

```groovy
originalJar = file("groovy/fandem.jar")
dexJar = file("groovy/fandem-dex.jar")
Dexter.convertJarToDexFile(originalJar, dexJar)
```

