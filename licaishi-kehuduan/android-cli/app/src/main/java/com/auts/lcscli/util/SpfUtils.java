package com.auts.lcscli.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.auts.lcscli.PhApplication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


/**
 * 封装SharedPreferences的工具类
 *
 * @author qisheng.lv
 * @date 2017/4/12
 * Copyright © 2015-2020 100msh.com All Rights Reserved
 */
public class SpfUtils {

    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_data";

    public SpfUtils() {
        /* cannot be instantiated */
        try {
            throw new UnsupportedOperationException("cannot be instantiated");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key    key
     * @param object value
     */
    public static void put(String key, Object object) {

        SharedPreferences sp = PhApplication.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (object == null) {
            object = "";
        }
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        editor.apply();

        //SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key           key
     * @param defaultObject value
     */
    public static Object get(String key, Object defaultObject) {
        SharedPreferences sp = PhApplication.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (defaultObject == null) {
            return "";
        } else if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return defaultObject;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key key
     */
    public static void remove(String key) {
        SharedPreferences sp = PhApplication.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        SharedPreferences sp = PhApplication.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key key
     * @return isContains
     */
    public static boolean contains(String key) {
        SharedPreferences sp = PhApplication.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context context
     * @return 全部键值对
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method SAPPLYMETHOD = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                LogUtils.debug(e);
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (SAPPLYMETHOD != null) {
                    SAPPLYMETHOD.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
                LogUtils.debug(e);
            } catch (IllegalAccessException e) {
                LogUtils.debug(e);
            } catch (InvocationTargetException e) {
                LogUtils.debug(e);
            }
            editor.commit();
        }
    }

}
