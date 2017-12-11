package com.lyldj.springboot.common.utils.sercurity;

import org.apache.commons.codec.binary.Base64;

/**
 * base64
 */
public class Base64Coder {

    public static byte[] decode(String key) {
        return Base64.decodeBase64(key);
    }

    public static String encode(byte[] key)  {
        return Base64.encodeBase64String(key);
    }

}
