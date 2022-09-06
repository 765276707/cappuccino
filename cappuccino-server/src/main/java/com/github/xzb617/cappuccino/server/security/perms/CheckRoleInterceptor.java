package com.github.xzb617.cappuccino.server.security.perms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.xzb617.cappuccino.server.base.ErrorResponse;
import com.github.xzb617.cappuccino.server.base.ErrorStatus;
import com.github.xzb617.cappuccino.server.security.Subject;
import com.github.xzb617.cappuccino.server.security.SubjectContextHolder;
import com.github.xzb617.cappuccino.server.utils.AnnotationUtil;
import com.github.xzb617.cappuccino.server.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckRoleInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    public CheckRoleInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取上下文的 Subject
        Subject subject = SubjectContextHolder.getContext();
        if (subject != null) {
            // 非匿名访问，判断是否需要进行权限校验
            CheckRole checkRole = AnnotationUtil.getMethodAnnotation(handler, CheckRole.class);
            if (checkRole != null) {
                // 接口上有该注解，进行权限校验
                Role role = checkRole.value();
                boolean access = subject.hasRole(role.getValue());
                if (!access) {
                    // 权限不足
                    ResponseUtil.writeJSON(response, HttpStatus.FORBIDDEN, getNoPermJson());
                    return false;
                }
            }
        }

        // 放行
        return true;
    }


    protected String getNoPermJson() throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(new ErrorResponse(ErrorStatus.INSUFFICIENT_ACCESS_AUTHORITY));
    }

}
