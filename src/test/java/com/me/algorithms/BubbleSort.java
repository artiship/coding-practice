package com.me.algorithms;

import org.junit.Assert;
import org.junit.Test;

public class BubbleSort {
    private int[] array = new int[]{4, 5, 6, 1, 3, 2};

    @Test public void
    index() {
        bubbleSort(array);

        Assert.assertArrayEquals(array, new int[]{1, 2, 3, 4, 5, 6});
    }

    private void bubbleSort(int[] arr) {
        for(int i = arr.length; i >= 0; i--) {
            boolean flag = false;
            for (int j = 0; j < i - 1; j++) {
                if(arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    flag = true;
                }
            }
            if(!flag) break;
        }
    }
}
