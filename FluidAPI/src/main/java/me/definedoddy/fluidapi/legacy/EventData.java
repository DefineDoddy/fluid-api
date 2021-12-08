package me.definedoddy.fluidapi.legacy;

import org.bukkit.event.EventPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventData {
    int delay() default 0;
    EventPriority priority() default EventPriority.NORMAL;
}
