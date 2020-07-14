package com.zw.knight.service;

import com.zw.knight.pojo.LinkNode;

import java.util.Random;

/**
 * 两数相加
 *
 * @author zw
 * @date 2020/7/13
 * 给出两个非空的链表，表示两个非负的整数。其中，他们各自的位数是按照逆序的方式存储的，并且他们的每个节点只能存储
 * 一位数字。
 * 如果，我们将两个数相加，返回一个新的链表，标识他们的和。
 * 除了0之外都不会以零开头。
 */
public class NO2 {
    LinkNode link1 = new LinkNode();

    LinkNode link2 = new LinkNode();

    public void add(LinkNode result, LinkNode link1, LinkNode link2, int temp) {
        if (link1 == null && link2 == null) {
            return;
        }
        int num = 0;
        if (link1 != null) {
            num += link1.getNum();
        }
        if (link2 != null) {
            num += link2.getNum();
        }
        if (temp != 0) {
            num += temp;
        }
        temp = num / 10;
        result.setNum(num % 10);
        LinkNode next1 = link1 != null && link1.getNext() != null ? link1.getNext() : null;
        LinkNode next2 = link2 != null && link2.getNext() != null ? link2.getNext() : null;
        if (next1 != null || next2 != null) {
            result.setNext(new LinkNode());
            this.add(result.getNext(), next1, next2, temp);
        }
    }

    public void printLink(LinkNode link) {
        if (link != null) {
            System.out.print(link.getNum());
            printLink(link.getNext());
        }
    }

    public void getDemo(LinkNode linkNode, int num) {
        if (num <= 0) {
            return;
        }
        Random rd = new Random();
        linkNode.setNum(rd.nextInt(8) + 1);
        num -= 1;
        if (num > 0) {
            linkNode.setNext(new LinkNode());
        }

        getDemo(linkNode.getNext(), num);
    }

    public static void main(String[] args) {
        NO2 no2 = new NO2();
        LinkNode result = new LinkNode();
        Random rd = new Random();
        int num1 = rd.nextInt(9);
        int num2 = rd.nextInt(9);
        System.out.println("num1: " + num1);
        System.out.println("num2: " + num2);
        LinkNode node1 = new LinkNode();
        LinkNode node2 = new LinkNode();
        no2.getDemo(node1, num1);
        no2.getDemo(node2, num2);
        System.out.println("-----------打印开始-----------");
        no2.printLink(node1);
        System.out.println();
        System.out.println("-----------打印结束-----------");
        System.out.println("-----------打印开始-----------");
        no2.printLink(node2);
        System.out.println();
        System.out.println("-----------打印结束-----------");
        long st = System.currentTimeMillis();
        no2.add(result, node1, node2, 0);
        System.out.println("两数和耗时：" + (System.currentTimeMillis() - st) + "毫秒");
        System.out.println("-----------结果打印开始-----------");
        no2.printLink(result);
        System.out.println();
        System.out.println("-----------结果打印结束-----------");
    }
}
