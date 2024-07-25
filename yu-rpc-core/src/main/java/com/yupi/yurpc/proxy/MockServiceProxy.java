package com.yupi.yurpc.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j

public class MockServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        Class<?> methodReturnType = method.getReturnType();
        log.info("methodReturnType:{}", methodReturnType);
        return getDefaultObject(methodReturnType);
    }

    /**
     * 生成指定类型的默认值对象
     */
    private Object getDefaultObject(Class<?> type) {
        if (type.isPrimitive()) {
            // 基本类型
            if (type == int.class) {
                return 0;
            }
            if (type == long.class) {
                return 0L;
            }
            if (type == short.class) {
                return (short) 0;
            }
            if (type == byte.class) {
                return (byte) 0;
            }
            if (type == float.class) {
                return 0.0f;
            }
            if (type == double.class) {
                return 0.0d;
            }
            if (type == char.class) {
                return '\u0000';
            }
            if (type == boolean.class) {
                return false;
            }
        }
        // 引用类型
        return null;
    }

}
