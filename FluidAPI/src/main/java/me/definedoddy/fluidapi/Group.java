package me.definedoddy.fluidapi;

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
}
