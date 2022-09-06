package com.github.xzb617.cappuccino.server.utils;

import com.github.xzb617.cappuccino.server.security.JwtClaims;
import io.jsonwebtoken.*;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    // 令牌建造器
    private final static JwtBuilder jwtBuilder = Jwts.builder();
    // 令牌解析器
    private final static JwtParser jwtParser = Jwts.parser();

    public final static String HEADER_TYP = "typ";
    public final static String HEADER_ALG = "alg";

    // 算法
    private static SignatureAlgorithm sign_alg = SignatureAlgorithm.HS256;

    /**
     * 创建令牌
     * @param claims jwtBody
     * @param secret 秘钥
     * @return
     */
    public static String createToken(JwtClaims claims, Long uid, String roles, String secret) {
        // 自定义属性
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("uid", uid);
        dataMap.put("roles", roles);
        // 构建
        jwtBuilder.setHeaderParam(HEADER_TYP, "jwt")
                .setHeaderParam(HEADER_ALG, sign_alg)
                .setClaims(dataMap)
                .setId(claims.getId())
                .setSubject(claims.getSubject())
                .setAudience(claims.getAudience())
                .setIssuedAt(claims.getIssuedAt())
                .setExpiration(claims.getExpiration())
                .signWith(sign_alg, secret);
        return jwtBuilder.compact();
    }

    /**
     * 刷新令牌
     * @param refreshToken 刷新的令牌
     * @param secret 秘钥
     * @return
     */
    public static String refreshToken(String refreshToken, Long uid, String roles, String secret) {
        // 解析刷新令牌
        JwtClaims claims = getJwtClaims(refreshToken, secret);
        // 构建
        return createToken(claims, uid, roles, secret);
    }

    /**
     * 获取JwtClaims
     * @param token
     * @param secret
     * @return
     */
    public static JwtClaims getJwtClaims(String token, String secret) {
        Claims body = jwtParser
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        // 匹配成功，则返回解析结果
        JwtClaims jwtClaims = new JwtClaims(body);
        jwtClaims.setUid(body.get("uid", Long.class));
        jwtClaims.setRoles(body.get("roles", String.class));
        return jwtClaims;
    }


    /**
     * 获取过期时间
     * @param token
     * @param secret
     * @return
     */
    public static Date getExpiration(String token, @NonNull String secret) {
        return getJwtClaims(token, secret).getExpiration();
    }

}
