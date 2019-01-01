package com.me.leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class ArrayAlgorithms {
    public int removeDuplicates(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }

        int counter = 1;
        for (int i = 1; i < nums.length; i++) {
            if(nums[i] != nums[i-1]) {
                nums[counter++] = nums[i];
            }
        }

        return counter;
    }

    public int maxProfit(int[] prices) {
        if(prices == null || prices.length < 2) {
            return 0;
        }

        int profit = 0;
        int currentProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            currentProfit = Math.max(currentProfit, currentProfit += prices[i] - prices[i-1]);
            profit = Math.max(currentProfit, profit);
        }

        return profit;
    }

    public void rotate(int[] nums, int k) {
        k %= nums.length;

        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length -1);
    }

    private void reverse(int[] nums, int start, int end) {
        while(start < end) {
            int temp = nums[end];
            nums[end] = nums[start];
            nums[start] = temp;
            start ++;
            end --;
        }
    }

    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int n: nums) {
            if(!set.add(n)) return true;
        }

        return false;
    }

    @Test public void test() {
        int[] nums = new int[]{1, 1, 2, 2, 2, 2};
        int counts = removeDuplicates(nums);
        Assert.assertEquals(2, counts);

        int[] prices = new int[]{1,2,3,4,5};
        int maxProfit = maxProfit(prices);
        Assert.assertEquals(4, maxProfit);

        int[] rotate = new int[]{1,2,3,4,5,6,7};
        rotate(rotate, 3);
        Assert.assertArrayEquals(rotate, new int[]{5,6,7,1,2,3,4});

        Assert.assertEquals(true, containsDuplicate(new int[]{1, 2, 1}));
        Assert.assertEquals(false, containsDuplicate(new int[]{1, 2, 3}));
    }
}
