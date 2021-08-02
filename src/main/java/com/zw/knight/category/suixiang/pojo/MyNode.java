/*
 * Copyright (c) 2021.得间科技
 * All rights reserved.
 */

package com.zw.knight.category.suixiang.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 我的链表
 *
 * @author zw
 * @date 2021/8/2
 */
@Data
@NoArgsConstructor
public class MyNode {
    /**
     * 值
     */
    private Object value;
    /**
     * 前
     */
    private MyNode previous;
    /**
     * 后
     */
    private MyNode next;

    private boolean isTail;

    public MyNode(Object value) {
        this.value = value;
    }
}
