package com.lyldj.springboot.common.utils.sercurity;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Hmac encrypt algorithm
 */
public class Hmac {
    private static final String HMAC_MD5 = "HmacMD5";
    private static final String HMAC_SHA_1 = "HmacSHA1";
    private static final String HMAC_SHA_256 = "HmacSHA256";
    private static final String HMAC_SHA_384 = "HmacSHA384";
    private static final String HMAC_SHA_512 = "HmacSHA512";

    public static String initKeyMD5() throws NoSuchAlgorithmException {
        return initKey(HMAC_MD5);
    }

    public static String initKeySHA1() throws NoSuchAlgorithmException {
        return initKey(HMAC_SHA_1);
    }

    public static String initKeySHA256() throws NoSuchAlgorithmException {
        return initKey(HMAC_SHA_256);
    }

    public static String initKeySHA384() throws NoSuchAlgorithmException {
        return initKey(HMAC_SHA_384);
    }

    public static String initKeySHA512() throws NoSuchAlgorithmException {
        return initKey(HMAC_SHA_512);
    }

    public static byte[] encryptMD5(byte[] data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        return encrypt(data, key, HMAC_MD5);
    }

    public static byte[] encryptSHA1(byte[] data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        return encrypt(data, key, HMAC_SHA_1);
    }

    public static byte[] encryptSHA256(byte[] data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        return encrypt(data, key, HMAC_SHA_256);
    }

    public static byte[] encryptSHA384(byte[] data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        return encrypt(data, key, HMAC_SHA_384);
    }

    public static byte[] encryptSHA512(byte[] data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        return encrypt(data, key, HMAC_SHA_512);
    }

    private static String initKey(String algorithm) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.encodeBase64String(secretKey.getEncoded());
    }

    private static byte[] encrypt(byte[] data, String key, String algorithm) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKey secretKey = new SecretKeySpec(Base64.decodeBase64(key), algorithm);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }


}
