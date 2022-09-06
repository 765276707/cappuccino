package com.github.xzb617.cappuccino.commons.utils;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesUtil {

    /**
     * 算法名称
     */
    private final static String ALG = "AES";
    /**
     * 填充方式
     */
    private final static String CBC_PADDING = "AES/CBC/PKCS5Padding";
    private final static String ECB_PADDING = "AES/EBC/PKCS5Padding";

    /**
     * 编码类型
     */
    private final static String ENCODED = "UTF-8";
    /**
     * 解密器
     */
    private static Cipher decryptCipher;
    /**
     * 加密器
     */
    private static Cipher encryptCipher;

    private static String IV = "1234567890123456";


    public static String encrypt(String plainText, String secret) throws Exception {
        if (encryptCipher == null) {
            encryptCipher = getCBCModeCipherInstance(ALG, CBC_PADDING, secret, IV, Cipher.ENCRYPT_MODE);
        }
        byte[] encryptedBytes = encryptCipher.doFinal(plainText.getBytes(ENCODED));
        return Base64.encodeBase64URLSafeString(encryptedBytes);
    }

    public static String decrypt(String cipherText, String secret) throws Exception {
        if (decryptCipher == null) {
            decryptCipher = getCBCModeCipherInstance(ALG, CBC_PADDING, secret, IV, Cipher.DECRYPT_MODE);
        }
        byte[] var1 = Base64.decodeBase64(cipherText);
        byte[] var2 = decryptCipher.doFinal(var1);
        return new String(var2);
    }

    /**
     * 获取 CBC 模式下的 Cipher
     * @param alg 算法名称
     * @param padding 填充模式
     * @param secret 秘钥
     * @param iv 偏移量
     * @param cipherMode 加密/解密模式
     * @return
     * @throws Exception
     */
    private static Cipher getCBCModeCipherInstance(String alg, String padding, String secret, String iv, int cipherMode) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), alg);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        // 指定加密的算法、工作模式和填充方式
        Cipher cipher = Cipher.getInstance(padding);
        cipher.init(cipherMode, secretKeySpec, ivParameterSpec);
        return cipher;
    }

    /**
     * 获取 ECB 模式下的 Cipher
     * @param secret 秘钥
     * @return Cipher
     * @throws Exception
     */
    private static Cipher getECBModeCipherInstance(String secret) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), ALG);
        // 指定加密的算法、工作模式和填充方式
        Cipher cipher = Cipher.getInstance(ECB_PADDING, new BouncyCastleProvider());
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher;
    }


}
