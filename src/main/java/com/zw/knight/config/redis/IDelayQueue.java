/*
 * Copyright (c) 2018.掌阅科技
 * All rights reserved.
 */

package com.zw.knight.config.redis;

/**
 * 延时队列
 *
 * @author WangBonan
 * @date 2018/5/24
 */
public interface IDelayQueue<E> {

    /**
     * 向延时队列中添加数据, 错误时记日志重试
     *
     * @param score 分数
     * @param data  数据
     * @return true 成功 false 失败
     */
    boolean add(long score, E data);

    /**
     * 从延时队列中获取数据
     *
     * @return
     */
    String get();

    /**
     * 删除数据
     *
     * @param data 数据
     * @return
     */
    boolean rem(E data);

}
