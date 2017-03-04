package com.jyq.android.common.sys;

import android.text.TextUtils;

import com.jyq.android.common.log.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * 通过反射获得对应函数功能
 */
public class ReflectionUtil {
    public static Object invokeSuperMethod(Object obj,String methodName,Class[] paramTypes,Object[] params){
        if (obj==null||TextUtils.isEmpty(methodName)){
            return null;
        }
        Class<?> clazz=obj.getClass().getSuperclass();
        try {
            Method method=clazz.getDeclaredMethod(methodName,paramTypes);
            method.setAccessible(true);
            return method.invoke(obj,params);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 通过类对象，运行指定方法
     * @param obj 类对象
     * @param methodName 方法名
     * @param params 参数值
     * @return 失败返回null
     */
    public static Object invokeMethod(Object obj, String methodName, Object[] params) {
        if (obj == null || TextUtils.isEmpty(methodName)) {
            return null;
        }

        Class<?> clazz = obj.getClass();
        try {
            Class<?>[] paramTypes = null;
            if (params != null) {
                paramTypes = new Class[params.length];
                for (int i = 0; i < params.length; ++i) {
                    paramTypes[i] = params[i].getClass();
                }
            }
            Method method = clazz.getMethod(methodName, paramTypes);
            method.setAccessible(true);
            return method.invoke(obj, params);
        } catch (NoSuchMethodException e) {
            LogUtil.i("reflect", "method " + methodName + " not found in " + obj.getClass().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static Object getFieldValue(Object obj, String fieldName) {
        if (obj == null || TextUtils.isEmpty(fieldName)) {
            return null;
        }

        Class<?> clazz = obj.getClass();
        while (clazz!=Object.class){
            try {
                Field field=clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            clazz=clazz.getSuperclass();
        }
        LogUtil.e("reflect", "get field " + fieldName + " not found in " + clazz.getName());
        return null;
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) {
        if (obj == null || TextUtils.isEmpty(fieldName)) {
            return;
        }

        Class<?> clazz = obj.getClass();
        while (clazz != Object.class) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(obj, value);
                return;
            } catch (Exception e) {
            }
            clazz = clazz.getSuperclass();
        }
        LogUtil.e("reflect", "set field " + fieldName + " not found in " + obj.getClass().getName());
    }
}