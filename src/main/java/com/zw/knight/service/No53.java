/*
 * Copyright (c) 2021.得间科技
 * All rights reserved.
 */

package com.zw.knight.service;

import com.google.gson.Gson;
import com.zw.knight.util.GsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。 要求时间复杂度为O(n)。
 * <p>
 * 输入: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 *
 * @author zw
 * @date 2021/7/8
 */
public class No53 {
    public int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};

    public void getMaxChild(int[] nums) {
        List<Integer> maxChild = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        int max = nums[0];
        int sum = 0;
        for (int num : nums) {
            if (sum < 0) {
                sum = num;
                temp = new ArrayList<>();
                temp.add(num);
            } else {
                sum = num + sum;
                max = Math.max(sum, max);
                if (sum == max){
                    maxChild = temp;
                    temp.add(num);
                }
            }
        }
        System.out.println(max);
        System.out.println(GsonUtils.toJson(maxChild));
    }

    public static void main(String[] args) {
        No53 no53 = new No53();
        no53.getMaxChild(no53.nums);
    }

}
