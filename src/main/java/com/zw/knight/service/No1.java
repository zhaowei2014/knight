package com.zw.knight.service;

import com.zw.knight.util.GsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和
 *
 * @author zw
 * @date 2020/7/14
 * 给定一个整数数据nums和一个目标值target,请你在该数组中找出和为目标值的两个整数，并返回他们的数组下标。
 * 你可以假设每种输入只会对应一个答案，但是数组中同一个元素不能重复使用
 */
public class No1 {
    Integer[] nums = {2,3,9,7,6};

    public Integer[] getIndex(Integer[] nums, Integer target) {
        Integer[] index = new Integer[2];
        Map<Integer, Integer> temp = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            temp.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            index[0] = i;
            if (temp.get(target - nums[i]) != null) {
                index[1] = temp.get(target - nums[i]);
                return index;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        No1 no1 = new No1();
        System.out.println(GsonUtils.toJson(no1.getIndex(no1.nums,10)));
    }
}
