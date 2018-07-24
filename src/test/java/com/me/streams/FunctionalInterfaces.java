package com.me.streams;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class FunctionalInterfaces {
    @Test public void
    map_compute_if_absent() {
        Map<String, Integer> nameMap = new HashMap<>();

        Integer value = nameMap.computeIfAbsent("hello", String::length);

        assertThat(value, is(5));
        assertThat(nameMap.get("hello"), is(5));
    }

    @Test public void
    function_apply() {
        Function<Integer, String> intToString = Object::toString;
        Function<String, String> quote = s -> "'" + s + "'";

        Function<Integer, String> quoteIntToString = quote.compose(intToString);

        assertEquals("'5", quoteIntToString.apply(5));
    }

    @FunctionalInterface
    public interface ShortToByteFunction {
        byte applyAsByte(short s);
    }

    public byte[] transformArray(short[] array, ShortToByteFunction function) {
        byte[] transformedArray = new byte[array.length];
        for(int i = 0;i < array.length; i++) {
            transformedArray[i] = function.applyAsByte(array[i]);
        }
        return transformedArray;
    }

    @Test public void
    primitive_function_test() {
        short[] array = {(short)1, (short)2, (short)3};

        byte[] transformedArray = transformArray(array, s -> (byte) (s * 2));

        assertArrayEquals(new byte[]{(byte) 2, (byte) 4, (byte) 6}, transformedArray);
    }

    /**
     * To define lambdas with two arguments, we have to use "Bi" keyword in thier names, BiFunction
     * ToDoubleBiFunction, ToIntBiFunction and ToLongBiFunction
     */
    @Test public void
    two_arguments_function() {
        Map<String, Integer> salaries = new HashMap<>();
        salaries.put("John", 40000);
        salaries.put("Freddy", 30000);
        salaries.put("Samuel", 50000);

        salaries.replaceAll((name, oldValue) ->
                name.equals("Freddy") ? oldValue : (oldValue + 10000));

        assertThat(salaries.get("John"), is(50000));
        assertThat(salaries.get("Freddy"), is(30000));
        assertThat(salaries.get("Samuel"), is(60000));
    }

    public double squareLazy(Supplier<Double> lazyValue) {
        return Math.pow(lazyValue.get(), 2);
    }

    /**
     * does not take any arguments, used for lazy generation of values
     */
    @Test public void
    supplier_test() {
        Supplier<Double> lazyValue = () -> {
          return 8d;
        };

        Double valueSquared = squareLazy(lazyValue);
    }

    /**
     * as opposed to the supplier, the consumer accepts a generified argumenet and
     * returns nothing, it is a function that is representing side effects
     */
    @Test public void
    consumers_test() {
        List<String> names = Arrays.asList("John", "Freddy", "Samuel");
        names.forEach(name -> System.out.println("Hello, "+ name));

        Map<String, Integer> ages = new HashMap<>();
        ages.put("John", 25);
        ages.put("Freddy", 24);
        ages.put("Samuel", 30);

        ages.forEach((name, age) -> System.out.println(name + "is " + age + "years old"));
    }

    /**
     * predicate receives a generified value and returns a boolean
     *
     * predicate lambda to filter a collection of values
     */
    @Test public void
    predicates_test() {
        List<String> names = Arrays.asList("Angela", "Aaron", "Bob", "Claire", "David");

        List<String> namesWithA = names.stream()
                .filter(name -> name.startsWith("A"))
                .collect(Collectors.toList());
    }

    /**
     * receive and return the same value type
     *
     */
    @Test public void
    operators_test() {
        List<String> names = Arrays.asList("Angela", "Aaron", "Bob", "Claire", "David");

        //unary operator
        names.replaceAll(String::toUpperCase);

        List<Integer> values = Arrays.asList(3, 5, 8, 9, 12);

        // BinaryOperator
        int sum = values.stream()
                .reduce(0, (i1, i2) -> i1 + i2);
    }

    /**
     * not all functional interfaces appeared in java 8
     * many interfaces from previous versions fo java conform to the constraints
     * of functionalinterface and can be used as lambda
     */
    @Test public void
    legacy_functional_interfaces() {
        Thread thread = new Thread(() -> System.out.println("helo"));
        thread.start();
    }
}
