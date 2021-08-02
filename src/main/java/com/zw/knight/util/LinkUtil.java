/*
 * Copyright (c) 2021.得间科技
 * All rights reserved.
 */

package com.zw.knight.util;

import com.zw.knight.category.suixiang.pojo.MyNode;

/**
 * 链表工具
 *
 * @author zw
 * @date 2021/8/2
 */
public class LinkUtil {

    public static MyNode getLink(Object[] array, int pos) {
        MyNode head = null;
        MyNode myNode = null;
        MyNode lastNext = null;
        for (int i = 0; i < array.length; i++) {
            MyNode tempNode = new MyNode(array[i]);
            if (myNode != null) {
                myNode.setNext(tempNode);
            } else {
                head = tempNode;
            }
            if (pos != -1 && i == pos) {
                lastNext = tempNode;
            }
            myNode = tempNode;
        }
        if (myNode != null) {
            myNode.setTail(true);
        }
        if (pos != -1 && myNode != null) {
            myNode.setNext(lastNext);
        }
        return head;
    }

    public static void printLink(MyNode myNode) {
        MyNode temp = myNode;
        while (temp != null) {
            System.out.println(temp.getValue());
            if (temp.isTail() && temp.getNext() == null) {
                temp = null;
                if (temp.getNext() != null){

                    System.out.println("tail next " + temp.getNext().getValue());
                }
            } else if (temp.isTail() && temp.getNext() != null) {
            } else {
                temp = temp.getNext();
            }
        }
    }

    public static void main(String[] args) {
        Object[] array = {1, 2, 3, 4};
        int pos = 2;
        printLink(getLink(array, pos));
    }
}
