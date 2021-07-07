import chart
import json

import java.text.SimpleDateFormat

class WorkoutData {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd")

    String name
    List<Float> weights = []
    List<Integer> reps = []
    List<Long> timestamps = []
    Boolean kgTotal = false

    @Override
    String toString() {
        return "WorkoutData(" +
                "weights=" + weights +
                ", reps=" + reps +
                ", timestamps=" + timestamps.collect { formatTimestamp(it) } +
                ')';
    }

    String formatTimestamp(Long aLong) {
        return DATE_FORMAT.format(aLong)
    }

    WorkoutData addEntry(Number weight, Integer rep) {
        return addEntry(new Date(), weight, rep)
    }

    WorkoutData addEntry(Long timestamp, Number weight, Integer rep) {
        timestamps.add(timestamp)
        weights.add(weight)
        reps.add(rep)
        return this
    }
    
    WorkoutData addEntry(Date date, Number weight, Integer rep) {
        return addEntry(date.time, weight, rep)
    }

    WorkoutData removeLastEntry() {
        weights.pop()
        reps.pop()
        timestamps.pop()
        return this
    }

    void show() {
        new MultiLineChart()
                .setSecondYAxisBounds(1, 20)
                .setTimeUnitX(java.util.concurrent.TimeUnit.DAYS)
                .setLineCharts([
                        new LineChart()
                            .setX(timestamps)
                            .setY(weights)
                            .setTitle(name)
                            .setYSuffix(kgTotal ? "kg au total" : "kg"),
                        new LineChart()
                            .setX(timestamps)
                            .setY(reps)
                            .setTitle("reps")
                            .setYSuffix(" reps")
                ])
                .show()
    }
}
/*PROMPT*/

class PrettyRootMap extends TreeMap {

    @Override
    String toString() {
        String INDENT = '    '
        def builder = new StringBuilder()
        builder.append('{\n')
        this.forEach() { muscle, muscleMap ->

            builder.append("$INDENT$muscle: {\n")
            builder.append(muscleMap)
            builder.append("$INDENT}\n")
        }
        builder.append('}\n')
        return builder.toString()
    }

    private void appendList(StringBuilder builder, List list, String indent) {
        builder.append('[\n')
        for (int i = 0; i < list.size(); i++) {
            builder.append(indent * 5).append(list[i])
            if (i < list.size() - 1) {
                builder.append(',')
            }
            builder.append('\n')
        }
        builder.append(indent * 4).append(']\n')
    }
}
/*PROMPT*/

class PrettyMap extends TreeMap {

    @Override
    String toString() {
        String INDENT = '    '
        def builder = new StringBuilder()
        this.forEach { exercise, data ->
            builder.append("$INDENT$INDENT$exercise: {\n")
            builder.append("$INDENT$INDENT${INDENT}weights: ")
            appendList(builder, data.weights, INDENT)
            builder.append("$INDENT$INDENT${INDENT}reps: ")
            appendList(builder, data.reps, INDENT)
            builder.append("$INDENT$INDENT${INDENT}timestamps: ")
            appendList(builder, data.timestamps.collect { data.formatTimestamp(it) }, INDENT)
            builder.append("$INDENT$INDENT}\n")
        }
        return builder.toString()
    }

    private void appendList(StringBuilder builder, List list, String indent) {
        builder.append('[\n')
        for (int i = 0; i < list.size(); i++) {
            builder.append(indent * 5).append(list[i])
            if (i < list.size() - 1) {
                builder.append(',')
            }
            builder.append('\n')
        }
        builder.append(indent * 4).append(']\n')
    }
    
    void show() {
        def lineCharts = this.values().collect { def data ->
            new LineChart()
                    .setX(data.timestamps)
                    .setY(data.weights)
                    .setTitle(data.name)
        }
        new MultiLineChart()
                .setTimeUnitX(java.util.concurrent.TimeUnit.DAYS)
                .setYSuffix("kg")
                .setLineCharts(lineCharts)
                .show()
    }
}
/*PROMPT*/


root = new File(envDir, "current")
if (!root.exists()) {
  root.mkdir()
}
_WEIGHT_FILE = new File(root, "weight.json")
if (!_WEIGHT_FILE.exists()) {
  def chart = new LineChart().setX([])
    .setY([])
    .setTitle("weight")
    .setTimeUnitX(DAYS)
    .setYSuffix("kg")
  _WEIGHT_FILE.text = JsonOutput.toJson(chart)
}
load = { File root ->
  loadData = { String muscle, File f ->
    def chart = new JsonSlurper().parseText(f.text).asType(CLASSES.WorkoutData)
    chart.metaClass.muscle { muscle }
    return chart
  }
  ALL = CLASSES.PrettyRootMap.newInstance()
  for (muscleFolder in root.listFiles()) {
    if (!muscleFolder.isDirectory()) continue
    String muscle = muscleFolder.name
    muscleMap = CLASSES.PrettyMap.newInstance()
    for (exerciseFile in muscleFolder.listFiles()) {
        String exercise = exerciseFile.name.replace(".json", "")
        def data = loadData(muscle, exerciseFile)
        muscleMap[exercise] = data
        setProperty(exercise, data)
    }
    setProperty(muscle, muscleMap)
    ALL[muscle] = muscleMap
  }

  weight = new JsonSlurper().parseText(_WEIGHT_FILE.text) as LineChart
  ALL
}

load(root)

// will be useful to load quickly old data
envDir.listFiles().findAll {
  it.isDirectory() && it.name != "current"
}.each {
  def name = "DATA_DIR" + it.name.replaceAll("-", "_") 
  setVariable(name, it)
}

save = { def data ->
    if (data == null) {
        data = LAST_RESULT
    }
    if (data.is(weight)) {
        _WEIGHT_FILE.text = JsonOutput.toJson(data)
        println "weight was successfully saved"
        return
    }
    File dir = new File(root, data.muscle())
    File f = new File(dir, data.name + ".json")
    if (!f.exists()) {
        f.createNewFile()
    }
    f.text = JsonOutput.toJson(data)
    println data.name + " was successfully saved in " + data.muscle()
}

saveAll = { ->
    for (def muscle in ALL.values()) {
        for (def exercise in muscle.values()) {
            save(exercise)
        }
    }
    save(weight)
}

createExercise = { String muscle, String name ->
    if (!ALL[muscle]) {
      throw new IllegalArgumentException("muscle '$muscle' doesn't exists")
    }
    def data = CLASSES.WorkoutData.newInstance(name: name)
    data.metaClass.muscle { muscle }
    ALL[muscle][name] = data
    setProperty(name, data)
    return data
}

createMuscle = { String name ->
  if (new File(root, name).mkdir()) {
    map = [:]
    ALL[name] = map
    setVariable(name, map)
    return true
  } else {
    return false
  }
}


ALL