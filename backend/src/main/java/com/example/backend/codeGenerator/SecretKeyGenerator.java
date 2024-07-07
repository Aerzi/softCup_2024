package com.example.backend.codeGenerator;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * @description 生成随机密钥
 * @author feixia0g
 * @date 2024/7/7 14:10
 */
public class SecretKeyGenerator {
    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32]; // 256 bits
        secureRandom.nextBytes(key);
        String base64Key = Base64.getEncoder().encodeToString(key);
        System.out.println("Your secret key: " + base64Key);
    }
}
