package com.lyldj.springboot.common.utils.sercurity;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * DESede对称加密算法演示
 *
 * @author zolly
 * */
public class DESede {
    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "DESede";

    /**
     * 加密/解密算法/工作模式/填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";

    /**
     * 生成密钥
     * @return byte[] 二进制密钥
     */
    public static String initkey() throws NoSuchAlgorithmException, IOException {
        // 实例化密钥生成器
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        kg.init(168);
        SecretKey secretKey = kg.generateKey();
        return Base64Coder.encode(secretKey.getEncoded());
    }

    /**
     * 转换密钥
     *
     * @param key
     *            二进制密钥
     * @return Key 密钥
     * */
    public static Key toKey(byte[] key) throws Exception {
        // 实例化Des密钥
        DESedeKeySpec dks = new DESedeKeySpec(key);
        // 实例化密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory
                .getInstance(KEY_ALGORITHM);
        // 生成密钥
        SecretKey secretKey = keyFactory.generateSecret(dks);
        return secretKey;
    }

    /**
     * 加密数据
     * @param data 待加密数据
     * @param key 密钥
     * @return byte[] 加密后的数据
     * */
    public static byte[] encrypt(byte[] data, String key) throws Exception {
        Key k = toKey(Base64Coder.decode(key));
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        // 初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    /**
     * 解密数据
     * @param data 待解密数据
     * @param key 密钥
     * @return byte[] 解密后的数据
     * */
    public static byte[] decrypt(byte[] data, String key) throws Exception {
        Key k = toKey(Base64Coder.decode(key));
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        // 初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }

}