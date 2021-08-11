---
description: This task is used for delaying actions being sent to the server.
---

# Delayed task

## Creating the task

To create a new delayed task, simply instantiate the **DelayedTask** class and override the **run\(\)** method:

```java
new DelayedTask(DELAY) {
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



