---
description: 'Setting specific properties to items, such as its display name, lore, etc.'
---

# Setting item properties

## Chain creation

If you want to create an item with properties all in one go, then just combine the methods of your choice below. Here is an example:

```java
ItemStack item = new FluidItem(Material.DIAMOND_SWORD).setName("Ruby Sword")
.setLore("&6Made from the", "&6finest of rubies").setModel(1).build();
```

The **build\(\)** method is what builds the item from a FluidItem to an ItemStack. You can still store the fluid item in a variable if you want to change values later on, but to get the item itself, you will need to call this method.

## Properties

**Display name**

```java
item.setName(DISPLAY NAME)
```

**Lore**

```java
item.setLore(LORE...)
```

```java
item.setLore(INDEX, LORE)
```

```java
item.setLore(INDEX[], LORE[])
```

```java
item.addLore(LORE...)
```

```java
item.addLore(ALLOW DUPLICATES, LORE...)
```

**Custom Model Data**

```java
item.setModel(ID)
```

**Item Flags**

```java
item.addFlags(FLAGS...)
```

```java
item.removeFlags(FLAGS...)
```

```java
item.removeFlags()
```

**Durability**

```java
item.setUnbreakable()
```

```java
item.setUnbreakable(UNBREAKABLE)
```

**Custom Data**

```java
item.setData(KEY, DATA TYPE, VALUE)
```

```java
item.getData(KEY, DATA TYPE)
```

```java
item.hasData(KEY, DATA TYPE)
```

**Enchantment**

```java
item.enchant(ENCHANTMENT, LEVEL)
```

```java
item.enchant(ENCHANTMENT, LEVEL, BYPASS LIMIT)
```

```java
item.addEnchantGlow()
```

