package com.me.collection;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ConvertCollections {
    @Test public void
    list_and_array() {
        List<Integer> sourceList = Arrays.asList(0, 1, 2, 3, 4, 5);
        sourceList.toArray(new Integer[sourceList.size()]);

        Integer[] sourceArray = {1, 2, 3, 4, 5};
        List<Integer> targetList = Arrays.asList(sourceArray);
    }
}
