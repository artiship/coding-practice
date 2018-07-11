package com.me;

import org.junit.Test;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.*;
import static org.junit.Assert.assertThat;

public class Collectors {
    @Test public void
    predefined_collectors() {
        List<String> givenList = Arrays.asList("a", "bb", "ccc", "dd");

        givenList.stream().collect(toList());
        givenList.stream().collect(toSet());
        givenList.stream().collect(toCollection(LinkedList::new));
        givenList.stream().collect(toMap(Function.identity(), String::length));
        givenList.stream().collect(toMap(Function.identity(), String::length, (i1, i2) -> i1));
        //givenList.stream().collect(collectingAndThen(toList(), ImmutableList::copyOf));
        givenList.stream().collect(joining());
        givenList.stream().collect(joining(""));
        givenList.stream().collect(joining(" ", "PRE-", "-POST"));
        givenList.stream().collect(counting());

        DoubleSummaryStatistics result = givenList.stream().collect(summarizingDouble(String::length));
        result.getAverage();
        result.getCount();
        result.getMax();
        result.getMin();
        result.getCount();
        result.getSum();

        givenList.stream().collect(groupingBy(String::length, toSet()));
        givenList.stream().collect(partitioningBy(s ->s.length() > 2));
    }
}
