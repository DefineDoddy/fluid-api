package me.definedoddy.fluidapi.legacy;

import java.util.Arrays;
import java.util.List;

public class Group {
    private final List<Object> objects;

    public Group(Object... objects) {
        this.objects = Arrays.stream(objects).toList();
    }

    public List<Object> toList() {
        return objects;
    }

    public Object[] toArray() {
        return objects.toArray();
    }

    public Group add(Object... objects) {
        this.objects.addAll(List.of(objects));
        return this;
    }

    public Group remove(Object... objects) {
        this.objects.removeAll(List.of(objects));
        return this;
    }

    public boolean contains(Object... objects) {
        for (Object object : objects) {
            if (!this.objects.contains(object)) {
                return false;
            }
        }
        return true;
    }
}
