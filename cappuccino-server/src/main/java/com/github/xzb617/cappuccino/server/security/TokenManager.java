package com.github.xzb617.cappuccino.server.security;

import cn.hutool.core.date.DateUtil;
import com.github.xzb617.cappuccino.server.domain.entity.User;
import com.github.xzb617.cappuccino.server.properties.ServerProperties;
import com.github.xzb617.cappuccino.server.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class TokenManager {

    private final static Logger LOGGER = LoggerFactory.getLogger(TokenManager.class);

    private final static String AUDIENCE = "config-server";
    /**
     * 过期时间为: 6小时
     */
    private final static int EXPIRE_TIME = 60*60*6;

    private final ServerProperties serverProperties;

    public TokenManager(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    /**
     * 创建令牌
     * @param user
     * @return
     */
    public String createToken(User user, String roles) {
        // 生成访问令牌
        JwtClaims accessClaims = this.buildClaims(user);
        return JwtUtil.createToken(accessClaims, user.getId(), roles, this.serverProperties.getTokenSecret());
    }

    /**
     * 刷新令牌
     * @param user
     * @return
     */
    public String refreshToken(User user, String roles) {
        return this.createToken(user, roles);
    }


    private JwtClaims buildClaims(User user) {
        // 计算时间
        Date issuedAt = new Date();
        Date expiration = DateUtil.offsetSecond(issuedAt, EXPIRE_TIME);
        return new JwtClaims(
                UUID.randomUUID().toString(),
                user.getUsername(),
                AUDIENCE,
                issuedAt,
                expiration
        );
    }

}
