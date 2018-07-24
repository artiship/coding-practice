package com.me.streams;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class Streams {

    @Test public void
    stream_find_any_test() {
        List<String> list = Arrays.asList("A", "B", "C", "D");

        Optional<String> result = list.stream().findAny();

        assertTrue(result.isPresent());
        assertThat(result.get(), anyOf(is("A"), is("B"), is("C"), is("D")));
    }

    @Test public void
    createParallelStream_whenFindAnyResultIsNotFirst_thenCorrect() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> result = list.stream().parallel()
                .filter(num -> num < 4)
                .findAny();

        assertTrue(result.isPresent());
        assertThat(result.get(), anyOf(is(1), is(2), is(3)));
    }

    @Test public void
    createStream_whenFindFirstResultIsPresent_thenCorrect() {
        List<String> list = Arrays.asList("A", "B", "C", "D");

        Optional<String> result = list.stream().findFirst();

        assertTrue(result.isPresent());
        assertThat(result.get(), is("A"));
    }

    @Test public void
    filtering_a_stream_of_optaionals() {
        List<Optional<String>> listOfOptionls = Arrays.asList(
                Optional.empty(), Optional.of("foo"), Optional.empty(), Optional.of("bar")
        );

        List<String> collect = listOfOptionls.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}