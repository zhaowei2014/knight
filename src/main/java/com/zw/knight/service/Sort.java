package com.zw.knight.service;

import com.zw.knight.util.GsonUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 排序
 *
 * @author zw
 * @date 2020/7/15
 */
public class Sort {
    /**
     * 二分排序  从小到大
     */
    public void doubleSort(int[] nums, int start, int end) {
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                int temp = nums[i];
                // 当前位置
                int left = 0;
                int right = i - 1;
                while (left <= right) {
                    int mid = (left + right) / 2;
                    if (nums[mid] < temp) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
                System.arraycopy(nums, left, nums, left + 1, i - left);
                nums[left] = temp;
            }
        }
    }

    /**
     * 归并排序 从小到大
     */
    public void mergeSort(int[] nums, int start, int end) {
        int[] result = new int[nums.length];
        if (start >= end) {
            return;
        }
        int len = end - start;
        int mid = (len >> 1) + start;
        int start1 = start;
        int start2 = mid + 1;
        mergeSort(nums, start1, mid);
        mergeSort(nums, start2, end);
        int k = start;
        while (start1 <= mid && start2 <= end) {
            result[k++] = nums[start1] < nums[start2] ? nums[start1++] : nums[start2++];
        }
        while (start1 <= mid) {
            result[k++] = nums[start1++];
        }
        while (start2 <= end) {
            result[k++] = nums[start2++];
        }
        if (end + 1 >= 0) {
            System.arraycopy(result, 0, nums, 0, end + 1);
        }
    }

    /**
     * 插入排序 从大到小
     */
    public void inSort(int[] nums, int start, int end) {
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

    // 堆排序 从大到小

    /**
     * 快速排序 从大到小
     */
    public void quickSort(int[] nums, int l, int r) {
        if (l >= r) {
            return;
        }
        int x = nums[l];
        int i = l;
        int j = r;
        while (i < j) {
            while (i < j && (x < nums[j])) {
                j--;
            }
            if (i < j) {
                nums[i++] = nums[j];
            }
            while (i < j && (x >= nums[i])) {
                i++;
            }
            if (i < j) {
                nums[j--] = nums[i];
            }
        }
        nums[i] = x;
        quickSort(nums, l, i - 1);
        quickSort(nums, i + 1, r);
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int[] nums = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        Sort sort = new Sort();
        // 插入排序
        sort.print("doubleSort", nums);
        sort.print("mergeSort", nums);
        sort.print("inSort", nums);
        sort.print("quickSort", nums);
    }

    private void print(String name, int[] nums) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int[] x = nums.clone();
        Method method = Sort.class.getDeclaredMethod(name, int[].class, int.class, int.class);
        long st = System.nanoTime();
        method.invoke(this, x, 0, nums.length - 1);
        System.out.println(name + "耗时:" + (System.nanoTime() - st));
        System.out.println(name + ": nums" + GsonUtils.toJson(x));
        System.out.println(name + ": nums" + GsonUtils.toJson(nums));
    }
}
