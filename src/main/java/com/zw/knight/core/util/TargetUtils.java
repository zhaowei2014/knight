package com.zw.knight.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * 获取目标对象
 *
 * @author jingsh
 * @date 2020/1/10
 */
public class TargetUtils {

    public static Object getTarget(Object proxy) throws Exception {
        if (!Proxy.isProxyClass(proxy.getClass())) {
            return proxy;
        }

        if (Proxy.isProxyClass(proxy.getClass())) {
            Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
            h.setAccessible(true);
            Object object = h.get(proxy);
            Field field = object.getClass().getDeclaredField("target");
            field.setAccessible(true);
            proxy = field.get(object);
        }
        return getTarget(proxy);
    }

}
