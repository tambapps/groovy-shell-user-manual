# Environments (pro feature)

Environments are basically scripts that you can easily load. They provide a practical way to load a script,
without having through the file picker every time you need it. 

Environments are also loadable from Shell Works scripts, so they are also useful to access variable/functions
in a Shell Works, instead of having to defining them again in the Shell Work script.

I personally use environments for scripts containing functions that I often use about a common use-case. 

For example, in the Shell Work script that you can see at the end of the 
[home page](https://tambapps.github.io/groovy-shell-user-manual/), an environment is
used. Several functions are defined:

- getNewEpisodes() : fetches the website and check for new episodes
- flushNewEpisodes() : saves the new episodes (in a file) so that in the next fetch, already seen
episodes won't be considered as new
  
With the environment, I would be able to use these functions from the Groovy Shell by using
the following command

```groovy
loadEnvironment "Website Check"
```


And I would also be able to use these functions from a Shell Work script with the same command.