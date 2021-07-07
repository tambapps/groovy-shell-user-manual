# Workout Charts

This environment allows you to track your progress for workout exercises.

You can find the environment script [here]().
Simply copy the text into a file, and create an environment (from the Groovy Shell) using this file

## Data structures

This environment relies on two data structures.

### Muscle

A muscle is simply a container of exercises. Grouping exercises by muscle
 allows you to display a chart having all exercises for this muscle
charts of all exercises 

### Exercise

This is the data structure that store your workout data. It contains your performance across time, and
can display them as a line chart.

An exercise displays 2 lines:
- the evolution of the number of reps you did for this exercise
- the evolution of the weight you lifted for this exercise


## How to use
### Create a muscle
First, you'll need to create muscles, that will later contain exercises.

```groovy
createMuscle('chest')
```

This will create a variable `chest` which is basically a Java Map exercise name -> exercise data
You can display all exercise names from a muscle like this

```groovy
chest.keySet()
```

### Create an exercise
After having created one muscle, you can link to it one/many exercise(s)

```groovy
// creating the exercise benchPress, associated to the muscle chest
createExercise('chest', 'benchPress')
```
Note that [Camel Case notation](https://en.wikipedia.org/wiki/Camel_case) notation is used. Using spaces in
exercise (and also muscle) names is discouraged because then you'll can't access the data from a shell variable
(variable names can't have spaces)

### Add an entry
At a given time, you lifted `m` kg in a serie of `n` reps. 
Here is an example on how to do it

```groovy
// you lifted 82.5kg, making 6 reps
float n = 82.5 
int m = 6
benchPress.addEntry(n, m)
```
This sample will add a data point for now, but If you want to add data for a specific time you can specify
a timestamp (`Long`)

```groovy
// import alias
import time
date = LocalDateTime.of(2021, 7, 7, 10, 30)
// you lifted 82.5kg, making 6 reps on 2021/07/07 at 10:30
benchPress.addEntry(date as Long, 82.5, 6)
```

You entered an entry and made a mistake? no panic, you can remove it calling the following function

```groovy
benchPress.removeLastEntry()
```

### Display charts

You can display the chart for one exercise
```groovy
benchPress.show()
```

Or for one muscle, it will display the evolution of the weights for all exercises linked
to this muscle
```groovy
chest.show()
```

### Save your changes
The operation presented above did update your data but it did not save/flush it.
Before leaving the shell you'll need to save your changes

You can save one exercise
```groovy
save(benchPress)
```

Or you can save all your changes in all your exercises
```groovy
saveAll()
```

### Track your weight
You can also track the evolution of your weight using the `weight` variable, which
is basically a [LineChart](https://tambapps.github.io/groovy-shell-user-manual/libraries/charts/javadoc/com/tambapps/android/grooidshell/chart/LineChart.html)

You can add your weight by simply calling `addEntry`
```groovy
weight.addEntry(81)
```
And display data like you would with an exercise, calling the `show()`


It is saved automatically when calling `saveAll()`

## Chart output example

Here is an example of chart of the `chest` muscle

![Charts screenshot](https://tambapps.github.io/groovy-shell-user-manual/images/chart_scrrenshot1.jpg "Charts screenshot")
