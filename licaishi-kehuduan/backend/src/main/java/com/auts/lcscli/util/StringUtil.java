package com.auts.lcscli.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 和String相关的工具类
 *
 * @author wenhua.tang
 */
public class StringUtil {
    private static final String EMPTY_STRING = "";
    private static final char[] RANDOM_CHARS = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ@._()"
            .toCharArray();

    /**
     * 空的String数组
     */
    public static final String[] EMPTY_ARRAY = new String[0];

    /**
     * 生成指定长度的随机字符串
     *
     * @param length
     * @return
     */
    public static String generateRandomString(int length) {
        return generateRandomString(RANDOM_CHARS, length);
    }

    /**
     * 生成指定长度指定范围的随机字符串
     *
     * @param randChars
     * @param length
     * @return
     */
    public static String generateRandomString(char[] randChars, int length) {
        if (length < 1) {
            return null;
        }

        Random rand = new Random();
        final int rLen = randChars.length;
        char[] newStr = new char[length];
        for (int i = 0; i < length; i++) {
            newStr[i] = randChars[rand.nextInt(rLen)];
        }

        return new String(newStr);
    }

    /**
     * 获取字符串数据
     *
     * @param
     * @return
     */
    public static String getStringValue(String obj) {
        if (null == obj) {
            return "";
        } else {
            return obj;
        }
    }

    /**
     * 生成全局唯一值
     *
     * @return
     */
    public static String generateGUID() {
        return replaceWithBlank(UUID.randomUUID().toString(), '-');
    }

    /**
     * 将字符串中的特定字符替换为空（即去掉此字符）
     *
     * @param
     * @return
     */
    public static String replaceWithBlank(String value, char oldChar) {
        if (null == value || value.length() == 0) {
            return value;
        }

        int len = value.length();
        int index = 0;
        char[] newValue = new char[len];
        for (int i = 0; i < len; i++) {
            char currentChar = value.charAt(i);
            if (currentChar != oldChar) {
                newValue[index++] = currentChar;
            }
        }

        return new String(newValue, 0, index);
    }

    /**
     * 将字节数组转换为16进制形式
     *
     * @param input
     * @return
     */
    public static String toHex(byte[] input) {
        if (input == null) {
            return null;
        }

        StringBuilder output = new StringBuilder(input.length * 2);
        for (int i = 0; i < input.length; i++) {
            int current = input[i] & 0xff;
            // 小于16的需要补充一位(共2位)
            if (current < 16) {
                output.append('0');
            }

            output.append(Integer.toString(current, 16));
        }

        return output.toString();
    }

    /**
     * 填充指定字符到字符串左边到指定长度
     *
     * @param orignalValue
     * @param padChar
     *            需要填充的字符
     * @param length
     *            填充后的长度
     * @return
     */
    public static String padLeft(String orignalValue, char padChar, int length) {
        return pad(orignalValue, padChar, length, true);
    }

    /**
     * 填充指定字符到字符串右边到指定长度
     *
     * @param orignalValue
     * @param padChar
     *            需要填充的字符
     * @param length
     *            填充后的长度
     * @return
     */
    public static String padRight(String orignalValue, char padChar, int length) {
        return pad(orignalValue, padChar, length, false);
    }

    /**
     * 填充指定字符到字符串的指定位置知道达到指定长度
     *
     * @param orignalValue
     * @param padChar
     *            需要填充的字符
     * @param length
     *            填充后的长度
     * @param isLeft
     *            是否填充在左边
     * @return
     */
    public static String pad(String orignalValue, char padChar, int length, boolean isLeft) {
        // null作为空字符串
        if (null == orignalValue) {
            orignalValue = EMPTY_STRING;
        }

        // 判断长度是否比需要的长度短，只有在短的情况下才会进行处理
        int oldLen = orignalValue.length();
        if (oldLen >= length) {
            return orignalValue;
        } else {
            int padLen = length - oldLen;
            // 得到需要添加的字符
            char[] appendChars = new char[padLen];
            Arrays.fill(appendChars, padChar);

            char[] newChars = new char[length];
            char[] orignalChars = orignalValue.toCharArray();
            if (isLeft) {
                System.arraycopy(appendChars, 0, newChars, 0, padLen);
                System.arraycopy(orignalChars, 0, newChars, padLen, oldLen);
            } else {
                System.arraycopy(orignalChars, 0, newChars, 0, oldLen);
                System.arraycopy(appendChars, 0, newChars, oldLen, padLen);
            }

            return new String(newChars);
        }
    }

    /**
     * 判读是否不为空
     */
    public static boolean isNotEmpty(String value) {
        return !isNullOrEmpty(value);
    }

    /**
     * 验证字符串是否为空或null
     *
     * @param value
     * @return
     */
    public static boolean isNullOrEmpty(String value) {
        return isNullOrEmpty(value, true);
    }

    /**
     * 验证字符串是否为空或null
     *
     * @param value
     * @return
     */
    public static boolean isNullOrEmpty(String value, boolean trim) {
        int len;
        if (null == value || (len = value.length()) == 0) {
            return true;
        }

        if (!trim) {
            return false;
        }

        for (int i = 0; i < len; i++) {
            if (value.charAt(i) != ' ') {
                return false;
            }
        }

        return true;
    }

    public static String subString(String text, int start, int length) {
        if (null == text || text.length() == 0) {
            return EMPTY_STRING;
        }

        int endPos = text.length();
        // 开始位置大于总长度，则返回空
        if (start >= endPos) {
            return EMPTY_STRING;
        }

        //
        if (start + length > endPos) {
            length = endPos - start;
        }

        return text.substring(start, start + length);
    }

    /**
     * 按照指定长度进行截取
     *
     * @param
     * @return
     */
    public static String getStringInLength(String value, int length) {
        if (StringUtil.isNullOrEmpty(value) || value.length() <= length) {
            return value;
        } else {
            return value.substring(0, length);
        }
    }

    public static long getIPNumber(String clientIP) {
        String[] ipSecs = split(clientIP, '.');
        if (null == ipSecs || ipSecs.length < 4) {
            return 0;
        }

        return (Long.parseLong(ipSecs[0]) << 24) + (Long.parseLong(ipSecs[1]) << 16) + (Long.parseLong(ipSecs[2]) << 8)
                + Long.parseLong(ipSecs[3]);
    }

    /**
     * 将字符串中的指定旧值替换成指定新值
     *
     * @param input
     * @param oldValue
     *            被替换的旧值
     * @param newValue
     *            替换成的新值
     * @return
     */
    public static String replaceLast(String input, String oldValue, String newValue) {
        final int lIndex = input.lastIndexOf(oldValue);
        if (lIndex < 0) {
            return input;
        }

        StringBuilder sb = new StringBuilder(input.length() - oldValue.length() + newValue.length());
        sb.append(input.substring(0, lIndex));
        sb.append(newValue);
        sb.append(input.substring(lIndex + oldValue.length()));

        return sb.toString();
    }

    /**
     * 将字符串中的指定旧值替换成指定新值
     *
     * @param input
     *            原字符串
     * @param oldValues
     *            被替换的旧值列表
     * @param newValues
     *            替换成的新值列表
     * @return
     */
    public static String replaceChars(String input, String[] oldValues, String[] newValues) {

        // 参数为空则返回原字符串
        if (StringUtil.isNullOrEmpty(input) || oldValues == null || newValues == null) {
            return input;
        }

        int size = oldValues.length;
        if (newValues.length != size) {
            return input;
        }

        for (int i = 0; i < size; i++) {
            input = input.replaceAll(oldValues[i], newValues[i]);
        }

        return input;
    }

    /**
     * 将字符串中的指定旧值替换成指定新值
     *
     * @param input
     *            原字符串
     * @param oldValue
     *            被替换的旧值
     * @param newValue
     *            替换成的新值
     * @return
     */
    public static String replaceChar(String input, String oldValue, String newValue) {

        // 参数为空则返回原字符串
        if (StringUtil.isNullOrEmpty(input) || StringUtil.isNullOrEmpty(oldValue)
                || StringUtil.isNullOrEmpty(newValue))
        {
            return input;
        }

        return input.replaceAll(oldValue, newValue);
    }

    /**
     * 直接针对String的split，不支持正则表达式
     *
     * @param
     * @return
     */
    public static String[] split(String value, char splitChar) {
        if (null == value) {
            return null;
        }

        int len = value.length();
        if (len == 0) {
            return EMPTY_ARRAY;
        }

        int lastFromIndex = 0; // 最近一次查询的其实位置
        int index;
        ArrayList<String> result = new ArrayList<String>();
        while ((index = value.indexOf(splitChar, lastFromIndex)) != -1) {
            if (lastFromIndex != index) {
                result.add(value.substring(lastFromIndex, index));
            }

            lastFromIndex = index + 1;
        }

        if (lastFromIndex >= 0 && lastFromIndex != len) {
            result.add(value.substring(lastFromIndex));
        }

        return result.toArray(new String[result.size()]);
    }

    /**
     * 直接针对String的split，不支持正则表达式
     *
     * @param
     * @return
     */
    public static String[] split(String value, String splitString) {
        if (null == value) {
            return null;
        }

        int len = value.length();
        if (len == 0) {
            return EMPTY_ARRAY;
        }

        int splitStringLen = splitString.length();
        if (splitStringLen == 0) {
            return new String[] { value };
        }

        int lastFromIndex = 0; // 最近一次查询的其实位置
        int index;
        ArrayList<String> result = new ArrayList<String>();
        while ((index = value.indexOf(splitString, lastFromIndex)) != -1) {
            if (lastFromIndex != index) {
                result.add(value.substring(lastFromIndex, index));
            }

            lastFromIndex = index + splitStringLen;
        }

        if (lastFromIndex >= 0 && lastFromIndex != len) {
            result.add(value.substring(lastFromIndex));
        }

        return result.toArray(new String[result.size()]);
    }

    /**
     * 获取截断后的字符串
     *
     * @param original
     *            原始字符串
     * @param maxLen
     *            截断后的最大长度
     * @return
     */
    public static final String getShortString(String original, int maxLen) {
        if (null == original) {
            return "";
        }

        if (original.length() > maxLen) {
            return original.substring(0, maxLen);
        }

        return original;
    }

    public static final int getCommonStrLength(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return 0;
        }
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();
        int len1 = str1.length();
        int len2 = str2.length();
        String min = null;
        String max = null;
        String target = null;
        min = len1 <= len2 ? str1 : str2;
        max = len1 > len2 ? str1 : str2;

        // 最外层：min子串的长度，从最大长度开始
        for (int i = min.length(); i >= 1; i--) {
            // 遍历长度为i的min子串，从0开始
            for (int j = 0; j <= min.length() - i; j++) {
                target = min.substring(j, j + i);
                // 遍历长度为i的max子串，判断是否与target子串相同，从0开始
                for (int k = 0; k <= max.length() - i; k++) {
                    if (max.substring(k, k + i).equals(target)) {
                        return i;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 方法名称:transMapToString 传入参数:map 返回值:String 形如 形如
     * username:chenziwen;password:1234
     */
    public static String transMapToString(Map<String, String> map, boolean isSort) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        List<String> keys = new ArrayList<String>(map.keySet());
        if (isSort) {
            Collections.sort(keys);
        }
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = map.get(key).toString();
            sb.append(key + ":" + value);
            sb.append(";");
        }
        String str = sb.toString();
        if (str.endsWith(";")) {
            str = str.substring(0, str.lastIndexOf(";"));
        }
        return str;
    }

    /**
     * 方法名称:transStringToMap 传入参数:mapString 形如 username:chenziwen;password:1234
     * 返回值:Map
     */
    public static Map<String, String> transStringToMap(String string) {
        Map<String, String> map = new HashMap<String, String>();
        if (isNullOrEmpty(string)) {
            return map;
        }
        String[] params = string.split(";");
        for (int i = 0; i < params.length; i++) {
            String[] str = params[i].split(":");
            if (str.length == 2) {
                map.put(str[0], str[1]);
            }
        }
        return map;
    }

    public static void main(String[] args) {
        // String s = "172.16.1.1:8080;172.16.1.2:8080";
        // p(StringUtil.split(s, ";"));
        // p(StringUtil.split(s, ';'));
        // p(s.split(";"));
        /// System.out.println(StringUtil.replaceLast("woshiashiyige", "shi",
        // "bushi"));
        // String[] oldValues = {"\"", "<", "/>"};
        // String[] newValues = {"&#34;", "&#60;", "/&#62;"};
        // System.out.println(StringUtil.replaceChars("dffd\"sf\"df<d<fsdfs/>df",
        // oldValues, newValues));

        // private static void p(String[] a) {
        // for(String s : a) {
        // System.out.println(s);
        // }
        // System.out.println("-----------");
        // }
        String str = "gateway:1000;lottery:500;mock:500";
        Map<String, String> map = transStringToMap(str);
        String t = transMapToString(map, true);
        System.out.println(t);
        System.out.println(map);
    }

    /**
     * Returns whether the given CharSequence contains only digits.
     */
    public static boolean isDigitsOnly(CharSequence str) {
        final int len = str.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if a and b are equal, including if they are both null.
     * <p>
     * <i>Note: In platform versions 1.1 and earlier, this method only worked
     * well if both the arguments were instances of String.</i>
     * </p>
     *
     * @param a
     *            first CharSequence to check
     * @param b
     *            second CharSequence to check
     * @return true if a and b are equal
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) {
            return true;
        }
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 把数字转成带小数点后面2位的字符串
     *
     * @param number
     * @return
     */
    public static String formatNumberWithTwoPoint(Number number) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(number);
    }

    public static String formatNumberWithTwoPoint(BigDecimal number) {
        return number.setScale(2, BigDecimal.ROUND_DOWN).toString();
    }

    /**
     * 确保字符串不为null
     *
     * @param str
     * @return
     */
    public static String makeStringNotNull(String str) {
        if (isNullOrEmpty(str)) {
            return "";
        }
        return str;
    }
}
