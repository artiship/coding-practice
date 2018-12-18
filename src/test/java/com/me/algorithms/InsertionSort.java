package com.me.algorithms;

import org.junit.Assert;
import org.junit.Test;

public class InsertionSort {
    private int[] array = new int[]{4, 5, 6, 1, 3, 2};

    @Test
    public void
    index() {
        this.insertionSort(array);
        Assert.assertArrayEquals(array, new int[]{1, 2, 3, 4, 5, 6});
    }

    private void insertionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int value = arr[i];
            int k = i - 1;
            for (; k >= 0; k--) {
                if(arr[k] <= value) {
                    break;
                }
                arr[k + 1] = arr[k];
            }
            arr[k + 1] = value;
        }
    }
}
