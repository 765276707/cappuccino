package com.github.xzb617.cappuccino.server.security.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.xzb617.cappuccino.commons.utils.StrUtil;
import com.github.xzb617.cappuccino.server.base.ErrorResponse;
import com.github.xzb617.cappuccino.server.base.ErrorStatus;
import com.github.xzb617.cappuccino.server.properties.ServerProperties;
import com.github.xzb617.cappuccino.server.security.JwtClaims;
import com.github.xzb617.cappuccino.server.security.Subject;
import com.github.xzb617.cappuccino.server.security.SubjectContextHolder;
import com.github.xzb617.cappuccino.server.security.perms.Role;
import com.github.xzb617.cappuccino.server.utils.AnnotationUtil;
import com.github.xzb617.cappuccino.server.utils.JwtUtil;
import com.github.xzb617.cappuccino.server.utils.ResponseUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AuthInterceptor implements HandlerInterceptor {

    @Value("${server.servlet.context-path}")
    private String servletContextPath;

    // 路径匹配器
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    // 没有配置token key，则默认使用该值
    private final static String DEFAULT_TOKEN_KEY_IN_HEADER = "Authorization";
    // 允许匿名访问的地址
    private static Set<String> anonymousUris = new HashSet<>();

    private final ServerProperties serverProperties;
    private final ObjectMapper objectMapper;

    static {
        // 登录登出
        anonymousUris.add("/auth/login");
        anonymousUris.add("/auth/logout");
        // 开放给客户端的接口也不需要身份验证
        anonymousUris.add("/clients/**");
        // 服务端节点之间的通讯接口
        anonymousUris.add("/cluster/**");
        // 静态资源
        anonymousUris.add("/static/**");
    }

    public AuthInterceptor(ServerProperties serverProperties, ObjectMapper objectMapper) {
        this.serverProperties = serverProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 排除哪些接口不需要令牌
        // 1.配置类的
        String uri = this.getEffectRequestURI(request);
        if (this.matchPath(uri)) {
            return true;
        }

        // 2.注解的
        boolean hasAnonymous = AnnotationUtil.hasMethodAnnotation(handler, Anonymous.class);
        if (hasAnonymous) {
            return true;
        }

        // 需要进行身份令牌校验
        String token = this.obtainTokenFromHeader(request);
        if (!StringUtils.isEmpty(token))
        {
            try
            {
                JwtClaims jwtClaims = JwtUtil.getJwtClaims(token, this.serverProperties.getTokenSecret());
                // 保存到上下文中
                SubjectContextHolder.setContext(new Subject(jwtClaims));
            }
            catch (JwtException e)
            {
                // 令牌过期或无效
                ResponseUtil.writeJSON(response, HttpStatus.UNAUTHORIZED, getUnauthorizedJson());
                return false;
            }
        }
        else
        {
            // 未携带令牌
            ResponseUtil.writeJSON(response, HttpStatus.UNAUTHORIZED, getUnauthorizedJson());
            return false;
        }

        // 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SubjectContextHolder.clearContext();
    }

    protected String getUnauthorizedJson() throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(new ErrorResponse(ErrorStatus.UNAUTHORIZED));
    }

    protected String obtainTokenFromHeader(HttpServletRequest request) {
        // 获取令牌配置信息
        String tokenKey = this.serverProperties.getTokenHeader();
        if (StringUtils.isEmpty(tokenKey)) {
            tokenKey = DEFAULT_TOKEN_KEY_IN_HEADER;
        }
        // 从Header中获取令牌
        return request.getHeader(tokenKey);
    }

    /**
     * 获取 ServletContextPath 之后的请求路径
     * @param request
     * @return
     */
    private String getEffectRequestURI(HttpServletRequest request) {
        // 解析
        String effectURI = request.getRequestURI();
        if (servletContextPath != null) {
            effectURI = effectURI.replace(servletContextPath, "");
        }
        return effectURI;
    }

    /**
     * 匹配路径无需身份验证
     * @param uri 访问路径
     * @return
     */
    private boolean matchPath(String uri) {
        if (anonymousUris.isEmpty()) {
            return false;
        }
        boolean result = false;
        for (String reg : anonymousUris) {
            if (antPathMatcher.match(reg, uri)) {
                result = true;
                break;
            }
        }
        return result;
    }

}
