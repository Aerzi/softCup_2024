package com.example.backend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class TokenUtils {
    /**
     * 生成token
     * @return
     */
    public static String genToken(String userId, String sign) {

        // 获取当前时间
        Date now = new Date();
        // 使用 Calendar 计算 2 小时后的时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR, 2);
        Date expiresAt = calendar.getTime();
        return JWT.create().withAudience(userId) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(expiresAt) // 2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }

}

