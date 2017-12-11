package com.lyldj.springboot.common.utils.sercurity;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * DH encrypt algorithm
 */
public abstract class DH {
    private static final String ALGORITHM = "DH";

    /**
     * Default Keysize 1024
     * Keysize must be a multiple of 64, ranging from 512 to 1024 (inclusive).
     */
    private static final int KEY_SIZE = 1024;

    /**
     * DH加密下需要一种对称加密算法对数据加密，这里我们使用DES，也可以使用其他对称加密算法。
     */
    public static final String SECRET_ALGORITHM = "DES";
    private static final String PUBLIC_KEY = "DHPublicKey";
    private static final String PRIVATE_KEY = "DHPrivateKey";

    /**
     * 加密<br>
     *
     * @param data       待加密数据
     * @param publicKey  甲方公钥
     * @param privateKey 乙方私钥
     * @return
     */
    public static byte[] encrypt(byte[] data, String publicKey,
                                 String privateKey) throws Exception {
        // 生成本地密钥
        SecretKey secretKey = getSecretKey(publicKey, privateKey);
        // 数据加密
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    /**
     * 解密<br>
     *
     * @param data       待解密数据
     * @param publicKey  乙方公钥
     * @param privateKey 乙方私钥
     */
    public static byte[] decrypt(byte[] data, String publicKey,
                                 String privateKey) throws Exception {
        // 生成本地密钥
        SecretKey secretKey = getSecretKey(publicKey, privateKey);
        // 数据解密
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return cipher.doFinal(data);
    }

    /**
     * 甲方密钥
     *
     * @return
     */
    public static Map<String, Object> initKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 甲方公钥
        DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
        // 甲方私钥
        DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<>(2);

        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 乙方密钥
     *
     * @param key 甲方公钥
     * @return
     */
    public static Map<String, Object> initKey(String key) throws NoSuchAlgorithmException,
            InvalidKeySpecException, InvalidAlgorithmParameterException {
        // 解析甲方公钥
        byte[] keyBytes = Base64Coder.decode(key);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        // 由甲方公钥构建乙方密钥
        DHParameterSpec dhParamSpec = ((DHPublicKey) pubKey).getParams();

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyFactory.getAlgorithm());
        keyPairGenerator.initialize(dhParamSpec);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 乙方公钥
        DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
        // 乙方私钥
        DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);

        return keyMap;
    }


    /**
     * 构建密钥
     *
     * @param publicKey  公钥
     * @param privateKey 私钥
     */
    private static SecretKey getSecretKey(String publicKey, String privateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        // 初始化公钥
        byte[] pubKeyBytes = Base64Coder.decode(publicKey);

        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKeyBytes);
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        // 初始化私钥
        byte[] priKeyBytes = Base64Coder.decode(privateKey);

        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(priKeyBytes);
        Key priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        KeyAgreement keyAgree = KeyAgreement.getInstance(keyFactory.getAlgorithm());
        keyAgree.init(priKey);
        keyAgree.doPhase(pubKey, true);
        // 生成本地密钥
        return keyAgree.generateSecret(SECRET_ALGORITHM);
    }

    /**
     * 取得私钥
     */
    public static String getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Coder.encode(key.getEncoded());
    }

    /**
     * 取得公钥
     */
    public static String getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Coder.encode(key.getEncoded());
    }
}