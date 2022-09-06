package com.github.xzb617.cappuccino.commons.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectUtil {

    /**
     * 获取类及其父类的所有属性名（终止类为 Object.class）
     * @param clazz 目标类
     * @return List<Field>
     */
    @SuppressWarnings("ALL")
    public static List<Field> getFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        // 当目标类的父类为null，则说明其已经是 Object.class
        while (clazz!=null && clazz.getSuperclass()!=null) {
            Field[] fields = clazz.getDeclaredFields();
            if (fields.length > 0) {
                fieldList.addAll(arrayToList(fields));
            }
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

    /**
     * 设置属性值
     * @param object 目标对象
     * @param field 属性
     * @param value 值
     * @throws IllegalAccessException
     */
    public static void setFieldValue(Object object, Field field, Object value) throws IllegalAccessException {
        if (!field.isAccessible()) {
            field.setAccessible(true);
            if (field.getType() == Boolean.class) {
                field.set(object, value == "true"? Boolean.TRUE :Boolean.FALSE);
            } else {
                field.set(object, value);
            }
            field.setAccessible(false);
        } else {
            field.set(object, value);
        }
    }

    /**
     * 设置属性值
     * @param object 目标对象
     * @param fieldName 属性名
     * @param value 值
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static void setFieldValue(Object object, String fieldName, Object value)
            throws IllegalAccessException, NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (!field.isAccessible()) {
            field.setAccessible(true);
            field.set(object, value);
            field.setAccessible(false);
        } else {
            field.set(object, value);
        }
    }

    private static List arrayToList(Object source) {
        AssertUtil.notNull(source, "ReflectUtil exec method arrayToList[source] : param can not be null.");
        return Arrays.asList(toObjectArray(source));
    }

    private static Object[] toObjectArray(Object source) {
        if (source instanceof Object[]) {
            return (Object[])((Object[])source);
        } else if (source == null) {
            return new Object[0];
        } else if (!source.getClass().isArray()) {
            throw new IllegalArgumentException("Source is not an array: " + source);
        } else {
            int length = Array.getLength(source);
            if (length == 0) {
                return new Object[0];
            } else {
                Class<?> wrapperType = Array.get(source, 0).getClass();
                Object[] newArray = (Object[])((Object[])Array.newInstance(wrapperType, length));

                for(int i = 0; i < length; ++i) {
                    newArray[i] = Array.get(source, i);
                }

                return newArray;
            }
        }
    }

}
