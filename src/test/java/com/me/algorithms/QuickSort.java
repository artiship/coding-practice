package com.me.algorithms;

import org.junit.Test;

import java.util.Arrays;

public class QuickSort {
    private int[] array = new int[]{8,4, 7, 10, 9, 2,3,6,1,5};

    @Test public void
    index () {
        _quick_sort(array, 0, array.length -1);
        Arrays.stream(array).forEach(System.out::println);
    }


    public void _quick_sort(int[] arr, int low, int high) {
        if(low > high) return;

        int p = _partition(arr, low, high);

        _quick_sort(arr, low, p - 1);
        _quick_sort(arr, p + 1, high);
    }

    public int _partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for(int j = low; j < high; j++) {
            if(arr[j] <= pivot) {
                i++;

                _swap(arr, i, j);
            }
        }

        _swap(arr, i+1, high);

        return i+1;
    }

    public void _swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

    }
}
