package com.auts.lcss.util;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wenhua.tang
 *
 */

public class MyListUtils {
    /**
     * 判断集合是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(final List<?> list) {
        return (list == null) || list.isEmpty();
    }

    /**
     * 字符串数组转换为List类型
     *
     * @param values
     * @return
     */
    public static List<String> asList(String[] values) {
        List<String> list = new LinkedList<String>();
        if ((values != null) && (values.length > 0)) {
            for (String value : values) {
                list.add(value);
            }
        }
        return list;
    }
}