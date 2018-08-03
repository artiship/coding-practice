package com.me.coreJava;

import org.junit.Test;

import java.util.StringJoiner;

import static org.junit.Assert.assertEquals;

public class JavaStringJoiner {
    @Test public void
    when_adding_elements_then_joined_elements() {
        String delimiter = ",";
        String prefix = "[";
        String suffix = "]";

        StringJoiner joiner = new StringJoiner(",", prefix, suffix);
        joiner.add("Red");
        joiner.add("Green");
        joiner.add("Blue");

        assertEquals(joiner.toString(), "[Red,Green,Blue]");
    }
}
