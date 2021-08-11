---
description: Creating a new ItemStack through the FluidItem class.
---

# Creating an item

To create an item, instantiate a new **FluidItem** by passing in a **Material** \(optional parameters being the amount, or creating an item from an existing one\) then building it:

```java
ItemStack item = new FluidItem(MATERIAL).build();
```

```java
ItemStack item = new FluidItem(MATERIAL, AMOUNT).build();
```

```java
ItemStack item = new FluidItem(ITEM STACK).build();
```

