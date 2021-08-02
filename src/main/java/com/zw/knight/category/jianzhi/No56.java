/*
 * Copyright (c) 2021.得间科技
 * All rights reserved.
 */

package com.zw.knight.category.jianzhi;

/**
 * 数组中数字出现的次数
 *
 * @author zw
 * @date 2021/7/28
 */
public class No56 {

    /**
     * 获取出现多次的数字
     */
    public static void getAllSingleNum(int[] data) {
        int a = 0;
        int b = 0;
        int k = 0;
        for (int d : data) {
            k ^= d;
        }
        int t = 1;
        while ((k & t) == 0) {
            t = t << 1;
        }
        for (int d : data) {
            if ((d & t) == 0) {
                a ^= d;
            } else {
                b ^= d;
            }
        }
        System.out.println(a + "," + b);
    }

    public static void getAllSingleNum2(int[] data) {
        String result = "";
        int temp = 0;
        int j = 0;
        for (int i = 0; i < data.length - 1; i++) {
            temp ^= data[i];
            int mod = 1;
            if (i % 2 == mod) {
                if (temp != 0) {
                    result = temp + ",";
                    mod ^= 1;
                    temp = 0;
                    j = 0;
                }
            }
            j++;
        }
        System.out.println(result);
    }

    public static void main(String[] args) {
        int[] a = {1, 1, 2, 2, 3, 3, 4, 5};
        getAllSingleNum(a);
    }
}
