package com.auts.lcscli.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by QiSheng on 2017/4/5.
 */
public class GenericUtils {
    public static Class getGenericClass(Class clasz) {
        return getGenericClass(clasz, 0);
    }

    public static Class getGenericClass(Class clasz, int index) {
        if (clasz == null) {
            return null;
        }
        Type type = clasz.getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            return null;
        }

        Type[] types = ((ParameterizedType) type).getActualTypeArguments();

        if (index < 0) {
            index = 0;
        }
        if (types == null || types.length <= index) {
            return null;
        }

        if (!(types[index] instanceof Class)) {
            return null;
        }

        return (Class) types[index];
    }
}
