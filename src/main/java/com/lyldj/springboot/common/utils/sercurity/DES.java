package com.lyldj.springboot.common.utils.sercurity;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * DES encrypt algorithm
 */
public abstract class DES {

    private static final String ALGORITHM = "DES";

    private static Key toKey(byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        return keyFactory.generateSecret(dks);
    }

    public static byte[] decrypt(byte[] data, String key) throws NoSuchAlgorithmException, InvalidKeyException,
            InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        Key k = toKey(Base64Coder.decode(key));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    public static byte[] encrypt(byte[] data, String key) throws NoSuchAlgorithmException, InvalidKeyException,
            InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        Key k = toKey(Base64Coder.decode(key));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    public static String initKey() throws NoSuchAlgorithmException {
        return initKey(null);
    }

    public static String initKey(String seed) throws NoSuchAlgorithmException {
        SecureRandom secureRandom;
        if (seed != null) {
            secureRandom = new SecureRandom(Base64Coder.decode(seed));
        } else {
            secureRandom = new SecureRandom();
        }
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
        kg.init(secureRandom);
        SecretKey secretKey = kg.generateKey();
        return Base64Coder.encode(secretKey.getEncoded());
    }
}