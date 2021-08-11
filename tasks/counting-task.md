---
description: >-
  This task is used for repeating actions being sent to the server, and then
  automatically stopping after a certain amount of runs.
---

# Counting task

## Creating the task

To create a new delayed task, simply instantiate the **CountingTask** class and override the **run\(\)** method:

```java
new CountingTask(INITIAL DELAY, REPEATING DELAY, COUNT) {
    @Override
    public void run() {
        //RUN SOMETHING
    }
};
```

You can also cancel a task before it is executed:

```java
task.cancel();
```

Additionally, you can get the task id by doing the following:

```java
int id = task.getId();
```

