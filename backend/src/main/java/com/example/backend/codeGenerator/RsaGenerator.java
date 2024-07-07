package com.example.backend.codeGenerator;

import java.security.*;
import java.util.Base64;

public class RsaGenerator {
    public static void main(String[] args) {
        try {
            // 生成KeyPairGenerator对象
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048, new SecureRandom());

            // 生成密钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            // 编码密钥为Base64字符串
            String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());

            // 输出密钥
            System.out.println("Private Key:");
            System.out.println(privateKeyBase64);
            System.out.println("Public Key:");
            System.out.println(publicKeyBase64);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
