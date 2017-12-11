package com.lyldj.springboot.common.utils.sercurity;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * AES encrypt algorithm
 */
public class AES {
    private static final String ALGORITHM = "AES";

    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";

    public static String initkey() throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
        //AES allow length 128,192,256
        kg.init(128);
        SecretKey secretKey = kg.generateKey();
        return Base64Coder.encode(secretKey.getEncoded());
    }

    public static String encrypt(String data, String key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            NoSuchProviderException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Key k = toKey(Base64Coder.decode(key));
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return Base64Coder.encode(cipher.doFinal(data.getBytes()));
    }

    public static String decrypt(String data, String key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            NoSuchProviderException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Key k = toKey(Base64Coder.decode(key));
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        cipher.init(Cipher.DECRYPT_MODE, k);
        return new String(cipher.doFinal(Base64Coder.decode(data)));
    }

    private static Key toKey(byte[] key) {
        return new SecretKeySpec(key, ALGORITHM);
    }

}