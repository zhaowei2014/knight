package com.zw.knight.core.aop;

import com.google.common.collect.Maps;
import com.zhangyue.common2.exception.ServiceException;
import com.zhangyue.dj.task.core.util.ThreadLocalUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 分表切面
 *
 * @author xyj
 * @date 2019/9/20
 */
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ShardingTableAop {
    private static final Logger LOG = LoggerFactory.getLogger(ShardingTableAop.class);

    @Pointcut("execution(public * com.zhangyue.dj.task.dao.mysql.*.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        Object object = null;
        Map<String, Object> nameAndArgs = null;
        try {
            nameAndArgs = getFieldsName(proceedingJoinPoint);
            ThreadLocalUtil.SHARDING_LOCAL.set(nameAndArgs);
            object = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            if (e instanceof ServiceException) {
                throw (ServiceException) e;
            } else {
                LOG.error("抛出异常:{},方法参数:{}", e.getMessage(), nameAndArgs);
                throw new ServiceException(e.getMessage());
            }
        } finally {
            ThreadLocalUtil.SHARDING_LOCAL.remove();
        }
        return object;
    }

    /**
     * 获取参数名和值
     *
     * @param proceedingJoinPoint
     * @return
     */
    private Map<String, Object> getFieldsName(ProceedingJoinPoint proceedingJoinPoint) {
        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] names = methodSignature.getParameterNames();
        Object[] args = proceedingJoinPoint.getArgs();
        Map<String, Object> hashMap = Maps.newHashMap();
        for (int i = 0; i < names.length; i++) {
            hashMap.put(names[i], args[i]);
        }
        return hashMap;
    }
}
