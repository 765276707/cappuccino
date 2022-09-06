package com.github.xzb617.cappuccino.server.utils;

import com.github.xzb617.cappuccino.commons.utils.DigestUtil;

import java.util.UUID;

/**
 * 密码加密器
 * @author xzb617
 */
public class PasswordEncryptor {

    /**
     * 加密字符串
     * @param source 字符串
     * @param secret 秘钥
     * @param salt 盐
     * @return String
     */
    public static String encrypt(String source, String secret, String salt) {
        String suffix = breakUp(secret + salt);
        String var = source + suffix;
        return DigestUtil.sha1(var, "utf-8");
    }

    /**
     * 校对两个字符串
     * @param oldSource 旧字符串（已加密）
     * @param newSource 新字符串（未加密）
     * @param secret 秘钥
     * @param salt 盐
     * @return boolean
     */
    public static boolean match(String oldSource, String newSource, String secret, String salt) {
        String encryptedNewSource = encrypt(newSource, secret, salt);
        return encryptedNewSource.equals(oldSource);
    }

    /**
     * 字符串打散
     * @param source 字符串
     * @return String
     */
    private static String breakUp(String source) {
        if (source==null || "".equals(source)) {
            throw new IllegalArgumentException("PasswordEncryptor break up source can not be null.");
        }
        int length = source.length();
        if (length == 1) {
            return source + "0a";
        } else if (length < 4) {
            return new StringBuilder(source).reverse().toString();
        } else if (length <= 6) {
            return reverse(source, length, 3);
        } else {
            return reverse(source, length, 5);
        }
    }

    private static String reverse(String source, int length, int size) {
        if (size >= length) {
            throw new IllegalArgumentException("PasswordEncryptor reverse size must less than length.");
        }
        String firstStr = source.substring(0, length-size);
        String lastStr = source.substring(length-size, length);
        return firstStr + new StringBuilder(lastStr).reverse().toString();
    }

    /**
     * 随机生成盐
     * @param length 盐的长度
     * @return
     */
    public static String generateSalt(int length) {
        String str = UUID.randomUUID().toString().replace("-", "");
        int len = str.length();
        if (length > len) {
            length = len;
        }
        return str.substring(0, length);
    }

}
