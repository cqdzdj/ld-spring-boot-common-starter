package com.lyldj.springboot.common.utils.sercurity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA encrypt algorithm
 */
public class SHA {

    private static final String SHA_1 = "SHA-1";
    private static final String SHA_256 = "SHA-256";
    private static final String SHA_384 = "SHA-384";
    private static final String SHA_512 = "SHA-512";

    public static byte[] sha1(byte[] data) throws NoSuchAlgorithmException {
        return sha(data, SHA_1);
    }

    public static byte[] sha256(byte[] data) throws NoSuchAlgorithmException {
        return sha(data, SHA_256);
    }

    public static byte[] sha384(byte[] data) throws NoSuchAlgorithmException {
        return sha(data, SHA_384);
    }

    public static byte[] sha512(byte[] data) throws NoSuchAlgorithmException {
        return sha(data, SHA_512);
    }

    private static byte[] sha(byte[] data, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance(algorithm);
        sha.update(data);
        return sha.digest();
    }

}
