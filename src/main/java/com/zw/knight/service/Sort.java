package com.zw.knight.service;

import com.zw.knight.util.GsonUtils;

/**
 * 排序
 *
 * @author zw
 * @date 2020/7/15
 */
public class Sort {
    Integer[] nums = {1};
    // 二分排序

    // 归并排序
    public void mergeSort(int[] nums){

    }

    // 插入排序
    public int[] inSort(int[] nums) {
        long st = System.nanoTime();
        int temp;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                temp = nums[i];
                for (int j = i; j >= 0; j--) {
                    if (j > 0 && temp < nums[j - 1]) {
                        nums[j] = nums[j - 1];
                    } else {
                        nums[j] = temp;
                        break;
                    }
                }
            }
        }
        System.out.println("耗时: " + (System.nanoTime() - st));
        return nums;
    }

    // 堆排序

    // 快速排序
    public void quickSort(int[] nums, int l, int r) {
        if (l < r) {
            int x = nums[l], i = l, j = r;
            while (i < j) {
                while (i < j && (x < nums[j])) j--;
                if (i < j) {
                    nums[i++] = nums[j];
                }
                while (i < j && (x >= nums[i])) i++;
                if (i < j) {
                    nums[j--] = nums[i];
                }
            }
            nums[i] = x;
            quickSort(nums, l, i - 1);
            quickSort(nums, i + 1, r);
        }
    }


    public static void main(String[] args) {
        int[] nums = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] nums1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Sort sort = new Sort();

        // 插入排序
//        System.out.println(GsonUtils.toJson(sort.inSort(nums)));
//        System.out.println(GsonUtils.toJson(sort.inSort(nums1)));
//
//        // 快速排序
//        long st = System.nanoTime();
//        sort.quickSort(nums1, 0, nums.length - 1);
//        System.out.println("quick耗时: " + (System.nanoTime() - st));
//        System.out.println(GsonUtils.toJson(nums));
//        long st1 = System.nanoTime();
//        sort.quickSort(nums1, 0, nums1.length - 1);
//        System.out.println("quick耗时: " + (System.nanoTime() - st1));
//        System.out.println(GsonUtils.toJson(nums1));

        // 归并排序
        System.out.println(50 >> 1);
    }
}
