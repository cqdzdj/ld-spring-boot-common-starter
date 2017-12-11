package com.lyldj.springboot.common.utils.sercurity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 encrypt algorithm
 */
public class MD5 {

    private static final String KEY_MD5 = "MD5";

    public static byte[] encrypt(byte[] data) throws NoSuchAlgorithmException {

        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);

        return md5.digest();

    }
}
