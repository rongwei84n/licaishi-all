package com.auts.lcscli.util;

import android.text.TextUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密
 * CBC加密，无偏移量。
 *
 * @author xiaolei.yang
 * @date 2017/11/17
 */

public class AESUtils {
    private static final String ALGORITHM = "AES/CBC/NoPadding";
    public static String key = "libosbsxsbsx1234";
    public static String iv = "libosbsxsbsx1234";

    public static String encrypt(String toEncryptData, String key, String iv) throws Exception {
        LogUtils.error("加密输入字符串", toEncryptData + "(不含括号及括号内容)");
        if (TextUtils.isEmpty(toEncryptData)) {
            return "";
        }
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        int blockSize = cipher.getBlockSize();

        byte[] dataBytes = toEncryptData.getBytes();
        int plaitTextLength = dataBytes.length;
        if (plaitTextLength % blockSize != 0) {
            plaitTextLength = plaitTextLength + (blockSize - (plaitTextLength % blockSize));
        }

        byte[] plainText = new byte[plaitTextLength];
        System.arraycopy(dataBytes, 0, plainText, 0, dataBytes.length);

        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(plainText);
        String entryResult = HexUtils.byte2HexStr(encrypted, encrypted.length);
        LogUtils.error("加密结果字符串", entryResult + "(不含括号及括号内容)");
        return entryResult;
    }

    public static String desEncrypt(String toDesEncryptData, String key, String iv) throws Exception {
        LogUtils.error("解密输入字符串", toDesEncryptData + "(不含括号及括号内容)");
        if (TextUtils.isEmpty(toDesEncryptData)) {
            return "";
        }
        byte[] encrypted1 = HexUtils.hexStr2Bytes(toDesEncryptData);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());

        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        byte[] original = cipher.doFinal(encrypted1);
        String desEncryptResult = new String(original);
        desEncryptResult = "A" + desEncryptResult;
        desEncryptResult = desEncryptResult.trim();
        desEncryptResult = desEncryptResult.replaceFirst("A", "");
        LogUtils.error("解密输出字符串", desEncryptResult + "(不含括号及括号内容)");
        return desEncryptResult;
    }
}
