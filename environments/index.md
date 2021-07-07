# Environments (pro feature)

Environments are basically scripts that you can easily load. They provide a practical way to load a script,
without having through the file picker every time you need it. 

Environments are also loadable from Shell Works scripts, so they are also useful to access variable/functions
in a Shell Works, instead of having to defining them again in the Shell Work script.

I personally use environments for scripts containing functions that I often use about a common use-case. 

You can find examples of environments [here](https://tambapps.github.io/groovy-shell-user-manual/environments/examples/)

## Environment variables

When loading an environment, the variable `envDir` is initialized. It is a `File`  of the directory of your environment. It can be useful if you want to store files specific to this environment. Note that you will find a `data.json` in this directory. This file should not be modified

## Defining classes in an environment

If you want to define classes you'll need to use the [special prompt comment](https://tambapps.github.io/groovy-shell-user-manual/shell-general/#special-comment-prompt)