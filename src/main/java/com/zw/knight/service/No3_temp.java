package com.zw.knight.service;

/**
 * 无重复字符最长子串
 *
 * @author zw
 * @date 2020/7/14
 * 给定一个字符串，请你找出其中无重复字符的最长子串的长度
 * 示例1
 * abcabcbb
 * abc  3
 * 示例2
 * bbbb
 * b 1
 */
public class No3_temp {
    private String getMaxChildLength(String str) {
        String real = "";
        int maxLength = 0;
        StringBuilder temp = new StringBuilder();
        char[] array = str.toCharArray();
        for (int i = 0; i < array.length; i++) {
            temp.append(array[i]);
            for (int j = i + 1; j < array.length; j++) {
                if (temp.toString().contains(array[j] + "")) {
                    if (temp.length() > maxLength) {
                        real = temp.toString();
                        maxLength = real.length();
                    }
                    break;
                }
                temp.append(array[j]);
            }
            temp = new StringBuilder();
        }
        return real;
    }

    public static void main(String[] args) {
        String test1 = "asdfsandf,mnsa,fdn,sfd";
        No3_temp no3 = new No3_temp();
        System.out.println(no3.getMaxChildLength(test1));
    }
}
