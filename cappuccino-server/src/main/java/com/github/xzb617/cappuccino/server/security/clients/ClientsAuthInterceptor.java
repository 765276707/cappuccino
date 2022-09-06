package com.github.xzb617.cappuccino.server.security.clients;

import com.github.xzb617.cappuccino.server.properties.ServerProperties;
import com.github.xzb617.cappuccino.server.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ClientsAuthInterceptor implements HandlerInterceptor {

    @Value("${server.servlet.context-path}")
    private String servletContextPath;
    /**
     * 路径匹配器
     */
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private Boolean clientAuthEnabled;
    private String clientAccessKey;
    private String clientAccessSecret;


    public ClientsAuthInterceptor(ServerProperties serverProperties) {
        this.clientAuthEnabled = serverProperties.getClientAuthEnabled();
        this.clientAccessKey = serverProperties.getClientAccessKey();
        this.clientAccessSecret = serverProperties.getClientAccessSecret();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (this.clientAuthEnabled) {
            String uri = this.getEffectRequestURI(request);
            if (this.matchPath(uri)) {
                // 获取认证信息
                String accessKey = request.getHeader("Access-Key");
                String accessSecret = request.getHeader("Access-Secret");
                // 校验
                if (this.clientAccessKey.equals(accessKey)
                        && this.clientAccessSecret.equals(accessSecret)) {
                    return true;
                } else {
                    ResponseUtil.writeJSON(response, HttpStatus.UNAUTHORIZED, "客户端认证失败，缺少必须的认证信息");
                    return false;
                }
            }
        }

        // 其余不予校验
        return true;
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

    private boolean matchPath(String uri) {
        return this.antPathMatcher.match("/clients/**", uri);
    }

}
