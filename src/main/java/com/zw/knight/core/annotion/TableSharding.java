package com.zw.knight.core.annotion;

import java.lang.annotation.*;

/**
 * 分表注解
 *
 * @author xyj
 * @date 2019/9/20
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TableSharding {
    /**
     * 分表表名
     *
     * @return
     */
    String tableName();

    /**
     * 分片数
     *
     * @return
     */
    int shardingNums() default 256;

    /**
     * 分片健，需要和传递参数名一致
     *
     * @return
     */
    String shardingKey();
}
