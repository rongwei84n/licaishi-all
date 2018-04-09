package com.auts.lcscli.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author xiaolei.yang
 * @date 2017/11/1
 */

public class StringUtils {
    /**
     * 获取字符串中中文字符的长度
     */
    public static int getChineseLength(String input) {
        if (input == null) {
            input = "";
        }
        // 中文
        String pattern = "[\u4e00-\u9fa5]";

        int chineseCount = 0;
        String temp;
        for (int i = 0; i < input.length(); i++) {
            temp = String.valueOf(input.charAt(i));
            if (temp.matches(pattern)) {
                chineseCount++;
            }
        }

        return chineseCount;
    }

    /**
     * 获取移除中文字符之后的字符串。
     */
    public static String getStringRemoveChinese(String input) {
        if (TextUtils.isEmpty(input)) {
            return "";
        }
        StringBuilder inputRemoveChinese = new StringBuilder("");

        // 中文
        String pattern = "[\u4e00-\u9fa5]";
        int inputLength = input.length();
        String temp;
        for (int i = 0; i < inputLength; i++) {
            temp = String.valueOf(input.charAt(i));
            if (!temp.matches(pattern)) {
                inputRemoveChinese.append(temp);
            }
        }

        return inputRemoveChinese.toString();

    }

    /**
     * 字符串是否包含非Ascii码编码32-126的字符
     *
     */

    public static boolean isContainNonAscii(String input) {
        if (TextUtils.isEmpty(input)) {
            input = "";
        }
        Pattern p = compile("[^\\x20-\\x7e]");
        // 判断ascii码
        Matcher m = p.matcher(input);
        return m.find();
    }

    /**
     * 把字符串中的非ASCII码替换为空字符串
     */

    public static String replaceNonAsciiToNullString(String input) {
        if (TextUtils.isEmpty(input)) {
            return "";
        }

        StringBuilder out = new StringBuilder("");
        for (int i = 0; i < input.length(); i++) {
            if (!isContainNonAscii(String.valueOf(input.charAt(i)))) {
                out.append(String.valueOf(input.charAt(i)));
            }
        }
        return out.toString();

    }
}
