package com.zw.knight.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 排序
 *
 * @author zw
 * @date 2020/7/15
 */
public class Sort {
    Integer[] nums = {1};

    // 二分排序
    public void doubleSort(int[] nums, int l, int r) {
        if (l >= r) return;


    }

    // 归并排序
    public void mergeSort(int[] nums, int start, int end) {
        int[] result = new int[nums.length];
        if (start >= end) return;
        int len = end - start;
        int mid = (len >> 1) + start;
        int start1 = start;
        int end1 = mid;
        int start2 = mid + 1;
        int end2 = end;
        mergeSort(nums, start1, end1);
        mergeSort(nums, start2, end2);
        int k = start;
        while (start1 <= end1 && start2 <= end2)
            result[k++] = nums[start1] > nums[start2] ? nums[start1++] : nums[start2++];
        while (start1 <= end1)
            result[k++] = nums[start1++];
        while (start2 <= end2)
            result[k++] = nums[start2++];
        for (int i = 0; i <= end; i++) {
            nums[i] = result[i];
        }
    }

    // 插入排序
    public void inSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                int temp = nums[i];
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

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int[] nums = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] nums1 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] result = new int[nums.length];
        Sort sort = new Sort();

        // 插入排序
        sort.inSort(nums);
        sort.print(sort, "inSort");
//        // 快速排序
//        long st = System.nanoTime();
//        sort.quickSort(nums1, 0, nums.length - 1);
//        long st1 = System.nanoTime();
//        sort.quickSort(nums1, 0, nums1.length - 1);
//        System.out.println("quick耗时: " + (st1 - st));
//        System.out.println("quick耗时: " + (System.nanoTime() - st1));
//
//        // 归并排序
//        long st3 = System.nanoTime();
//        sort.mergeSort(nums, result, 0, nums.length - 1);
//        long st4 = System.nanoTime();
//        sort.mergeSort(nums1, result, 0, nums1.length - 1);
//        System.out.println("merge耗时: " + (st4 - st3));
//        System.out.println("merge耗时: " + (System.nanoTime() - st4));
    }

    private void print(Object obj, String name) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int[] nums = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        Method method = Sort.class.getMethod(name, Sort.class);
        Sort sort = new Sort();
        Method[] methods = Sort.class.getMethods();
        for (Method method1 : methods) {
            System.out.println(method1.getName());
        }
//        long st = System.nanoTime();
//        method.invoke(obj, nums, 0, nums.length);
//        System.out.println(name + "耗时:" + (System.nanoTime() - st));
    }
}
