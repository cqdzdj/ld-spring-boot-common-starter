package com.lyldj.springboot.common.utils.sercurity;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

/**
 * PBE encrypt algorithm
 */
public abstract class PBE {
    /**
     * 支持以下任意一种算法
     *
     * <pre>
     * PBEWithMD5AndDES
     * PBEWithMD5AndTripleDES
     * PBEWithSHA1AndDESede
     * PBEWithSHA1AndRC2_40
     * </pre>
     */
    private static final String PBE_WITH_MD5_AND_DES = "PBEWithMD5andDES";
    private static final String PBE_WITH_MD5_AND_TRIPLE_DES = "PBEWithMD5AndTripleDES";
    private static final String PBE_WITH_SHA1_AND_DESEDE = "PBEWithSHA1AndDESede";
    private static final String PBE_WITH_MD5_AND_RC2_40 = "PBEWithSHA1AndRC2_40";

    public static byte[] initSalt() {
        byte[] salt = new byte[8];
        Random random = new Random();
        random.nextBytes(salt);
        return salt;
    }

    public static byte[] encryptWithMD5andDES(byte[] data, String password, byte[] salt)
            throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        return encrypt(data, password, salt, PBE_WITH_MD5_AND_DES);
    }

    public static byte[] encryptWithMD5AndTripleDES(byte[] data, String password, byte[] salt)
            throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        return encrypt(data, password, salt, PBE_WITH_MD5_AND_TRIPLE_DES);
    }

    public static byte[] encryptWithSHA1AndDESede(byte[] data, String password, byte[] salt)
            throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        return encrypt(data, password, salt, PBE_WITH_SHA1_AND_DESEDE);
    }

    public static byte[] encryptWithSHA1AndRC2_40(byte[] data, String password, byte[] salt)
            throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        return encrypt(data, password, salt, PBE_WITH_MD5_AND_RC2_40);
    }

    public static byte[] decryptWithMD5andDES(byte[] data, String password, byte[] salt)
            throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        return decrypt(data, password, salt, PBE_WITH_MD5_AND_DES);
    }

    public static byte[] decryptWithMD5AndTripleDES(byte[] data, String password, byte[] salt)
            throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        return encrypt(data, password, salt, PBE_WITH_MD5_AND_TRIPLE_DES);
    }

    public static byte[] decryptWithSHA1AndDESede(byte[] data, String password, byte[] salt)
            throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        return encrypt(data, password, salt, PBE_WITH_SHA1_AND_DESEDE);
    }

    public static byte[] decryptWithSHA1AndRC2_40(byte[] data, String password, byte[] salt)
            throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        return encrypt(data, password, salt, PBE_WITH_MD5_AND_RC2_40);
    }

    /**
     * 转换密钥<br>
     * @param password
     */
    private static Key toKey(String password, String algorithm) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
        return keyFactory.generateSecret(keySpec);
    }

    /**
     * 加密
     * @param data 数据
     * @param password 密码
     * @param salt  盐
     */
    public static byte[] encrypt(byte[] data, String password, byte[] salt, String algorithm) throws InvalidKeySpecException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Key key = toKey(password, algorithm);
        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
        return cipher.doFinal(data);

    }

    /**
     * 解密
     * @param data  数据
     * @param password 密码
     * @param salt  盐
     */
    public static byte[] decrypt(byte[] data, String password, byte[] salt, String algorithm) throws InvalidKeySpecException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Key key = toKey(password, algorithm);
        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        return cipher.doFinal(data);

    }
}