# Additional classpath (pro feature)
The additional classpath allows you to load additional jars everytime you
start a shell (shell works included).

It can be managed via the Preferences screen, at the `Additional classpath` item.

Each jar added to your additional classpath will be copied into a private directory (different from your
local repository), so that each time you starts a shell, all jars from this directory will be loaded.

You can provide both dex jars or non dex jars, the Android Groovy Shell will convert it 
(in the copy, without modifying the original jar) if necessary.


You can consult your class path with the following code example

```groovy

import dex
classPathHandler = new ClassPathHandler()
println classPathHandler.getClassPath()
```
