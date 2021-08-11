---
description: >-
  Setting specific properties to messages, such as its senders, type, and
  prefix.
---

# Setting message properties

## Chain creation

If you want to create a message with properties all in one go, then just combine the methods of your choice below. Here is an example:

```java
FluidMessage message = new FluidMessage("Hello world!").usePrefix()
.setType(Type.ACTIONBAR).send();
```

The **send\(\)** method is what sends the message to the receivers, however, you can still store the fluid message in a variable if you want to change values later on. This allows for sending different messages to different players all in one line. Here is another example:

```java
FluidMessage message = new FluidMessage("Hello player 1", player1).usePrefix()
.send().setReceivers(player2).setMessage("Hello player 2").send();
```

## Properties

**Prefix**

```java
message.usePrefix()
```

```java
message.setUsePrefix(USE PREFIX)
```

```java
message.setPrefix(PREFIX)
```

**Type**

```java
message.setType(TYPE)
```

**Message**

```java
message.setMessage(MESSAGE)
```

**Receivers**

```java
message.setReceivers(PLAYERS...)
```

```java
message.addRecievers(PLAYERS...)
```

```java
message.removeReceivers(PLAYERS...)
```

```java
message.removeReceivers()
```

