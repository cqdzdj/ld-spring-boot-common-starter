package com.lyldj.springboot.common.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;

import java.lang.reflect.*;
import java.util.*;

/**
 * @description: 生成对象
 * @author: duanjian
 * @date: 17-12-14 上午10:24
 */
public class InstanceGenerator {

    public static <T> T generte(Class<T> cls) {
        T t = null;
        try {
            t = cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Method[] ms = t.getClass().getDeclaredMethods();
        for (int i = 0; i < ms.length; i++) {
            try {
                Method method = ms[i];
                Class<?> params[] = method.getParameterTypes();
                if (method.getName().startsWith("set") && params.length == 1) {
                    if (params[0] == String.class) {
                        method.invoke(t, RandomStringUtils.random(10));
                    } else if (params[0] == Long.class) {
                        Class<?> clazz = Class.forName(getObjectType(cls, method));
                        if (clazz.isEnum()) {
                            Method valueMethod = clazz.getDeclaredMethod("getValue");
                            method.invoke(t, valueMethod.invoke(clazz.getEnumConstants()[0], null));
                        } else {
                            method.invoke(t, RandomUtils.nextLong(0, 10));
                        }
                    } else if (params[0] == Integer.class) {
                        method.invoke(t, RandomUtils.nextInt(0, 10));
                    } else if (params[0] == DateTime.class) {
                        method.invoke(t, DateTime.now());
                    } else if (params[0] == Date.class) {
                        method.invoke(t, new Date());
                    } else if (params[0].isEnum()) {
                        method.invoke(t, params[0].getEnumConstants()[0]);
                    } else if (params[0] == List.class) {
                        String typeName = getGenericType(cls, method, 0);
                        List list = new ArrayList<>();
                        if("java.lang.Object".equals(typeName)){
                            list.add(new HashMap<>());
                        } else {
                            list.add(generte(Class.forName(typeName)));
                        }
                        method.invoke(t, list);
                    } else if (params[0] == Set.class) {
                        String typeName = getGenericType(cls, method, 0);
                        Set set = new HashSet<>();
                        if("java.lang.Object".equals(typeName)){
                            set.add(new HashMap<>());
                        } else {
                            set.add(generte(Class.forName(typeName)));
                        }
                        method.invoke(t, set);
                    } else if (params[0] == Map.class) {
                        method.invoke(t, Collections.EMPTY_MAP);
                    } else if (params[0] == Boolean.class || params[0] == boolean.class) {
                        method.invoke(t, false);
                    } else if (params[0] == Object.class) {
                        method.invoke(t, new HashMap<>());
                    } else {
                        String typeName = getObjectType(cls, method);
                        method.invoke(t, generte(Class.forName(typeName)));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    private static <T> String getGenericType(Class<T> cls, Method setMethodName, int idx) throws NoSuchFieldException {
        String property = setMethodName.getName().substring(3, 4).toLowerCase() + setMethodName.getName().substring(4);
        Field field = cls.getDeclaredField(property);
        Type gType = field.getGenericType();
        if (gType instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) gType;
            return pType.getActualTypeArguments()[idx].getTypeName();
        } else {
            return Object.class.getName();
        }
    }

    private static <T> String getObjectType(Class<T> cls, Method setMethodName) throws NoSuchFieldException {
        String property = setMethodName.getName().substring(3, 4).toLowerCase() + setMethodName.getName().substring(4);
        Field field = cls.getDeclaredField(property);
        Type type = field.getType();
        return type.getTypeName();
    }

}
