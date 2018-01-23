package com.auts.lcssv.util;

import java.lang.reflect.ParameterizedType;

/**
 * 类相关的工具类
 * Created by xiaolei.yang on 2017/7/14.
 */

public class ClassUtils<T> {

    //获取泛型类的类名（暂未使用测试，如果使用后可以用就删掉括号这段注释）
    public String getClassName() {
        try {
            Class<T> clazz = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
            return clazz.getClass().getSimpleName();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
