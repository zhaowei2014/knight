package com.zw.knight.core.util;

import java.util.Map;

/**
 * 分表工具类
 *
 * @author xyj
 * @date 2019/9/20
 */
public class ThreadLocalUtil {
    public static final ThreadLocal<Map<String, Object>> SHARDING_LOCAL = new ThreadLocal<>();
}
