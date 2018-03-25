package com.auts.lcscli.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密相关工具类.
 * Created by rongwei.huang on 2018/02/12 on the train G6486.
 */

public class EntryUtils {

    public static final String PADDING_MODE = "AES/ECB/PKCS5Padding";

    /**
     * 获取MD5加密.
     *
     * @param str 需要加密的字符串
     * @return String字符串 加密后的字符串
     */
    public static String getMd5(String str) {
        try {
            // 创建加密对象
            MessageDigest digest = MessageDigest.getInstance("md5");

            // 调用加密对象的方法，加密的动作已经完成
            byte[] bs = digest.digest(str.getBytes("utf-8"));

            // 接下来，我们要对加密后的结果，进行优化，按照mysql的优化思路走
            // mysql的优化思路：
            // 第一步，将数据全部转换成正数：
//            String hexString = "";
            StringBuffer sbHexString = new StringBuffer("");
            for (byte b : bs) {
                // 第一步，将数据全部转换成正数：
                // 解释：为什么采用b&255
                /*
                 * b:它本来是一个byte类型的数据(1个字节) 255：是一个int类型的数据(4个字节)
                 * byte类型的数据与int类型的数据进行运算，会自动类型提升为int类型 eg: b: 1001 1100(原始数据)
                 * 运算时： b: 0000 0000 0000 0000 0000 0000 1001 1100 255: 0000
                 * 0000 0000 0000 0000 0000 1111 1111 结果：0000 0000 0000 0000
                 * 0000 0000 1001 1100 此时的temp是一个int类型的整数
                 */
                int temp = b & 255;
                // 第二步，将所有的数据转换成16进制的形式
                // 注意：转换的时候注意if正数>=0&&<16，那么如果使用Integer.toHexString()，可能会造成缺少位数
                // 因此，需要对temp进行判断
                if (temp < 16 && temp >= 0) {
                    // 手动补上一个“0”
//                    hexString = hexString + "0" + Integer.toHexString(temp);
                    sbHexString.append('0').append(Integer.toHexString(temp));
                } else {
//                    hexString = hexString + Integer.toHexString(temp);
                    sbHexString.append(Integer.toHexString(temp));
                }
            }
            //将字符串转成大写
            return sbHexString.toString().toUpperCase(Locale.CHINA);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 加密.
     *
     */
    public static String aesEncrypt(String sSrc, String sKey) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sKey.getBytes(Charset.forName("UTF-8")));
            byte[] keyss = md.digest();

            SecretKeySpec skeySpec = new SecretKeySpec(keyss, "AES");
            Cipher cipher = Cipher.getInstance(PADDING_MODE);// "算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes(Charset.forName("UTF-8")));
//            for (byte b : encrypted) {
//                Log.sd("EntryUtils", "b: " + b);
//            }
            return parseByte2HexStr(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密.
     *
     */
    public static String aesDecrypt(String sSrc, String sKey) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sKey.getBytes(Charset.forName("UTF-8")));
            byte[] keyss = md.digest();

            SecretKeySpec skeySpec = new SecretKeySpec(keyss, "AES");
            Cipher cipher = Cipher.getInstance(PADDING_MODE);// "算法/模式/补码方式"
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(parseHexStr2Byte(sSrc));
            return new String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                sb.append('0').append(hex.toUpperCase(Locale.CHINA));
//                hex = '0' + hex;
            } else {
                sb.append(hex.toUpperCase(Locale.CHINA));
            }
//            sb.append(hex.toUpperCase(Locale.CHINA));
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


}

