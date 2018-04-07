package com.auts.lcscli.util;

import android.text.TextUtils;

import java.util.Locale;

/**
 * hex转换工具类
 *
 * @author xiaolei.yang
 * @date 2017/11/17
 */

public class HexUtils {
    private static final char[] mChars = "0123456789ABCDEF".toCharArray();
    private static final String mHexStr = "0123456789ABCDEF";

    /**
     * 检查16进制字符串是否有效
     *
     * @param sHex String 16进制字符串
     * @return boolean
     */
    public static boolean checkHexStr(String sHex) {
        if (TextUtils.isEmpty(sHex)) {
            return false;
        }
        String sTmp = sHex.trim().replace(" ", "").toUpperCase(Locale.US);
        int iLen = sTmp.length();

        if (iLen > 1 && iLen % 2 == 0) {
            for (int i = 0; i < iLen; i++) {
                if (!mHexStr.contains(sTmp.substring(i, i + 1))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 字符串转换成十六进制字符串
     *
     * @param str String 待转换的ASCII字符串
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    public static String str2HexStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        byte[] bs = str.getBytes();
        if (bs.length == 0) {
            return "";
        }
        for (byte b : bs) {
            sb.append(mChars[(b & 0xFF) >> 4]);
            sb.append(mChars[b & 0x0F]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * 十六进制字符串转换成 ASCII字符串
     *
     * @param hexStr String Byte字符串
     * @return String 对应的字符串
     */
    public static String hexStr2Str(String hexStr) {
        if (TextUtils.isEmpty(hexStr)) {
            return "";
        }
        hexStr = hexStr.trim().replace(" ", "").toUpperCase(Locale.US);
        char[] hexChars = hexStr.toCharArray();
        byte[] byteChars = new byte[hexStr.length() / 2];
        int iTmp;

        for (int i = 0; i < byteChars.length; i++) {
            iTmp = mHexStr.indexOf(hexChars[2 * i]) << 4;
            iTmp |= mHexStr.indexOf(hexChars[2 * i + 1]);
            byteChars[i] = (byte) (iTmp & 0xFF);
        }
        return new String(byteChars);
    }

    /**
     * bytes转换成十六进制字符串
     *
     * @param b    byte[] byte数组
     * @param iLen int 取前N位处理 N=iLen
     * @return String 每个Byte值之间空格分隔
     */
    public static String byte2HexStr(byte[] b, int iLen) {
        if (null == b || b.length == 0 || iLen <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < iLen; n++) {
            sb.append(mChars[(b[n] & 0xFF) >> 4]);
            sb.append(mChars[b[n] & 0x0F]);
//            sb.append(' ');
        }
        return sb.toString().trim().toUpperCase(Locale.US);
    }

    /**
     * bytes字符串转换为Byte值
     *
     * @param src String Byte字符串，每个Byte之间没有分隔符(字符范围:0-9 A-F)
     * @return byte[]
     */
    public static byte[] hexStr2Bytes(String src) {
        if (TextUtils.isEmpty(src)) {
            return new byte[0];
        }
        /*对输入值进行规范化整理*/
        src = src.trim().replace(" ", "").toUpperCase(Locale.US);
        //处理值初始化
        int m;
        int n;
        //计算长度
        int iLen = src.length() / 2;
        //分配存储空间
        byte[] ret = new byte[iLen];
        if (iLen > 0) {
            for (int i = 0; i < iLen; i++) {
                m = i * 2 + 1;
                n = m + 1;
                ret[i] = (byte) (Integer.decode("0x" + src.substring(i * 2, m) + src.substring(m, n)) & 0xFF);
            }
        }
        return ret;
    }

    /**
     * String的字符串转换成unicode的String
     *
     * @param strText String 全角字符串
     * @return String 每个unicode之间无分隔符
     * @throws Exception
     */
    public static String strToUnicode(String strText) throws Exception {
        if (TextUtils.isEmpty(strText)) {
            return "";
        }
        char c;
        StringBuilder str = new StringBuilder();
        int intAsc;
        String strHex;
        for (int i = 0; i < strText.length(); i++) {
            c = strText.charAt(i);
            intAsc = (int) c;
            strHex = Integer.toHexString(intAsc);
            if (intAsc > 128) {
                str.append("\\u");
            } else { // 低位在前面补00

                str.append("\\u00");
            }

            str.append(strHex);
        }
        return str.toString();
    }

    /**
     * unicode的String转换成String的字符串
     *
     * @param hex String 16进制值字符串 （一个unicode为2byte）
     * @return String 全角字符串
     */
    public static String unicodeToString(String hex) {
        if (TextUtils.isEmpty(hex)) {
            return "";
        }
        int t = hex.length() / 6;
        int iTmp;
        StringBuilder str = new StringBuilder();
        if (t > 0) {
            for (int i = 0; i < t; i++) {
                String s = hex.substring(i * 6, (i + 1) * 6);
                // 将16进制的string转为int
                iTmp = (Integer.valueOf(s.substring(2, 4), 16) << 8) | Integer.valueOf(s.substring(4), 16);
                // 将int转换为字符
                str.append(new String(Character.toChars(iTmp)));
            }
        }
        return str.toString();
    }
}
