---
description: How to setup the Fluid API to work in your project.
---

# Installation

## Setting up a project

At the moment, the simplest way to integrate Fluid into your project is by adding the Maven dependency:

{% code title="pom.xml" %}
```markup
<repository>
	<id>jitpack.io</id>
	<url>https://jitpack.io</url>
</repository>

<dependency>
	<groupId>com.github.DefineDoddy</groupId>
	<artifactId>fluid-api</artifactId>
	<version>VERSION</version>
</dependency>
```
{% endcode %}

{% hint style="warning" %}
 In order for the API to work, you need to make sure the dependency is included in the final jar
{% endhint %}



