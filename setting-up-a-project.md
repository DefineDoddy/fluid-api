---
description: How to register your plugin with the Fluid API.
---

# Setting up a project

## Registering the plugin

In order for the Fluid API to recognise your plugin, you will need to register it before you call any fluid methods. This is best suited in the **onEnable\(\)** method of your class which extends from the **JavaPlugin** class.

```java
FluidPlugin.register(PLUGIN, NAME)
```

```java
FluidPlugin.register(PLUGIN, NAME, CHAT PREFIX)
```

{% hint style="danger" %}
 If you don't register your plugin, the API will not know where to look and will result in errors.
{% endhint %}



