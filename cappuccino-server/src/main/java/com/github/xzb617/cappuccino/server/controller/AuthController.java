package com.github.xzb617.cappuccino.server.controller;

import com.github.xzb617.cappuccino.commons.utils.StrUtil;
import com.github.xzb617.cappuccino.server.base.AjaxResponse;
import com.github.xzb617.cappuccino.server.domain.dto.AuthDTO;
import com.github.xzb617.cappuccino.server.domain.entity.User;
import com.github.xzb617.cappuccino.server.domain.vo.AuthVO;
import com.github.xzb617.cappuccino.server.properties.ServerProperties;
import com.github.xzb617.cappuccino.server.security.Subject;
import com.github.xzb617.cappuccino.server.security.SubjectContextHolder;
import com.github.xzb617.cappuccino.server.security.TokenManager;
import com.github.xzb617.cappuccino.server.service.UserService;
import com.github.xzb617.cappuccino.server.utils.PasswordEncryptor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthController {

    private final UserService userService;
    private final TokenManager tokenManager;
    private final ServerProperties serverProperties;

    public AuthController(UserService userService, TokenManager tokenManager, ServerProperties serverProperties) {
        this.userService = userService;
        this.tokenManager = tokenManager;
        this.serverProperties = serverProperties;
    }

    @PostMapping("/login")
    public AjaxResponse login(@RequestBody AuthDTO dto) {
        // 查询用户匹配密码
        User user = this.userService.getByName(dto.getUsername());
        if (user == null) {
            return AjaxResponse.failure("用户名或密码错误");
        }
        boolean matched = PasswordEncryptor.match(user.getPassword(), dto.getPassword(), this.serverProperties.getPasswordEncryptSecret(), user.getSalt());
        if (!matched) {
            return AjaxResponse.failure("用户名或密码错误");
        }
        // 封装返回数据
        String roles = user.getRole();
        String token = this.tokenManager.createToken(user, roles);
        return AjaxResponse.success()
                .message("登录成功")
                .data(new AuthVO(token, user.getUsername(), StrUtil.strToSet(roles), new Date()));
    }

    @DeleteMapping("/logout")
    public AjaxResponse logout() {
        return AjaxResponse.success().message("登出成功");
    }

    /**
     * 获取当前登录用户的数据
     * 必须持有令牌才能访问接口
     * @return
     */
    @GetMapping("/getInfo")
    public AjaxResponse getInfo() {
        // 解析令牌
        Subject subject = SubjectContextHolder.getContext();
        Map<String, Object> data = new HashMap<>(2);
        data.put("username", subject.getUsername());
        data.put("roles", subject.getRoles());
        return AjaxResponse.success()
                .message("查询成功").data(data);
    }

    /**
     * 获取登录用户的个人资料
     * @return
     */
    @GetMapping("/getLoginSelf")
    public AjaxResponse getLoginSelf() {
        Subject subject = SubjectContextHolder.getContext();
        User user = this.userService.getByName(subject.getUsername());
        user.setPassword("******");
        return AjaxResponse.success().data(user);
    }

}
