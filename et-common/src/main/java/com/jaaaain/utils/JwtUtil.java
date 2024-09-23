package com.jaaaain.utils;

import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Data
@Component
public class JwtUtil {
    /**
     * 生成jwt
     * 使用Hs256算法, 私匙使用固定秘钥
     *
     * @param secretKey jwt秘钥
     * @param ttlMillis jwt过期时间(毫秒)
     * @param claims    设置的信息
     * @return
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 指定签名的时候使用的签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成JWT的过期时间
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        // 设置jwt的body
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8))
                .setExpiration(exp);

        return builder.compact();
    }

    /**
     * Token解密
     *
     * @param secretKey jwt秘钥 一定要保留好在服务端, 不能暴露, 如果对接多个客户端建议改造成多个
     * @param token     加密后的token
     * @return
     */
    public static Claims parseJWT(String token, String secretKey) {
        try {
            System.out.println("解析时的 token: " + token);
            System.out.println("解析时的 secretKey: " + secretKey);

            return Jwts.parser()
                       .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))  // 明确指定 UTF-8
                       .parseClaimsJws(token)
                       .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expired");
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid token signature");
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
    }


}