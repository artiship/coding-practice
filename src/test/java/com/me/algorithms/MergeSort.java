package com.me.algorithms;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class MergeSort {
    private int[] array = new int[]{8,4, 7, 10, 9, 2,3,6,1,5};

    @Test public void
    index() {
        int[] expected = Arrays.copyOf(array, array.length);
        Arrays.sort(expected);
        int[] actual = Arrays.copyOf(array, array.length);
        mergeSort(actual, array.length);
        Assert.assertArrayEquals(actual, expected);
    }

    private void mergeSort(int[] arr, int length) {
        sort(arr, 0, length -1);
    }

    private void sort(int[] arr, int start, int end) {
        if(start >= end) {
            return;
        }

        int middle = start + (end - start) / 2;
        sort(arr, start, middle);
        sort(arr, middle + 1, end);

        merge(arr, start, middle, end);
    }

    private static void merge(int[] a, int p, int q, int r) {
        int i = p;
        int j = q+1;
        int k = 0; // 初始化变量i, j, k
        int[] tmp = new int[r-p+1]; // 申请一个大小跟a[p...r]一样的临时数组
        while (i<=q && j<=r) {
            if (a[i] <= a[j]) {
                tmp[k++] = a[i++]; // i++等于i:=i+1
            } else {
                tmp[k++] = a[j++];
            }
        }

        // 判断哪个子数组中有剩余的数据
        int start = i;
        int end = q;
        if (j <= r) {
            start = j;
            end = r;
        }

        // 将剩余的数据拷贝到临时数组tmp
        while (start <= end) {
            tmp[k++] = a[start++];
        }

        // 将tmp中的数组拷贝回a[p...r]
        for (i = 0; i <= r-p; ++i) {
            a[p+i] = tmp[i];
        }
    }
}
