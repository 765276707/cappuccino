package com.github.xzb617.cappuccino.server.service.impl;

import com.github.xzb617.cappuccino.server.base.TextCondition;
import com.github.xzb617.cappuccino.server.domain.condition.UserCondition;
import com.github.xzb617.cappuccino.server.domain.dto.ModifyPasswordDTO;
import com.github.xzb617.cappuccino.server.domain.dto.UserDTO;
import com.github.xzb617.cappuccino.server.domain.entity.User;
import com.github.xzb617.cappuccino.server.exception.ServiceException;
import com.github.xzb617.cappuccino.server.mapper.UserMapper;
import com.github.xzb617.cappuccino.server.properties.ServerProperties;
import com.github.xzb617.cappuccino.server.security.SecurityCapability;
import com.github.xzb617.cappuccino.server.service.UserService;
import com.github.xzb617.cappuccino.server.utils.PasswordEncryptor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl extends SecurityCapability implements UserService {

    /**
     * 未配置初始化密码的情况下，采用此默认初始密码
     */
    private final static String USER_INIT_PASSWORD = "11111111";

    private final UserMapper userMapper;
    private final ServerProperties serverProperties;

    public UserServiceImpl(UserMapper userMapper, ServerProperties serverProperties) {
        this.userMapper = userMapper;
        this.serverProperties = serverProperties;
    }

    @Override
    public User getById(Long id) {
        return this.userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getByName(String username) {
        return this.userMapper.selectByUsername(username);
    }

    @Override
    public List<User> getList(UserCondition condition) {
        return this.userMapper.selectList(condition);
    }

    @Override
    @Transactional
    public void save(UserDTO dto) {
        // 判断用户名、手机号是否已被使用
        long nameCount = this.userMapper.countByUsername(dto.getUsername());
        if (nameCount > 0) {
            throw new ServiceException("用户名已被使用");
        }
        long phoneCount = this.userMapper.countByPhoneNumber(dto.getPhoneNumber());
        if (phoneCount > 0) {
            throw new ServiceException("手机号已被注册");
        }
        // 获取用户初始密码参数
        String userInitPassword = this.serverProperties.getUserInitPassword();
        String initPassword = userInitPassword==null?USER_INIT_PASSWORD:userInitPassword;
        // 加密密码
        String salt = PasswordEncryptor.generateSalt(6);
        String encryptedInitPassword = PasswordEncryptor.encrypt(initPassword, serverProperties.getPasswordEncryptSecret(), salt);
        // 新增用户
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setPassword(encryptedInitPassword);
        user.setSalt(salt);
        user.setEnableWrite(true);
        user.setCreateTime(new Date());
        user.setCreateUser(getLoginName());
        this.userMapper.insertSelective(user);
    }

    @Override
    @Transactional
    public void update(UserDTO dto) {
        // 获取要编辑的用户
        User user = this.getById(dto.getId());
        if (user == null) {
            throw new ServiceException("要编辑的用户不存在或已被删除");
        }
        // 判断手机号是否已被使用
        if (!dto.getPhoneNumber().equalsIgnoreCase(user.getPhoneNumber())) {
            long phoneCount = this.userMapper.countByPhoneNumber(dto.getPhoneNumber());
            if (phoneCount > 0) {
                throw new ServiceException("手机号已被注册");
            }
        }
        // 编辑用户
        BeanUtils.copyProperties(dto, user, "id","username");
        user.setUpdateTime(new Date());
        user.setUpdateUser(getLoginName());
        this.userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    @Transactional
    public void deleteById(List<Long> ids) {
        // 删除用户
        this.userMapper.deleteByIds(ids);
    }

    @Override
    @Transactional
    public void resetPassword(Long id) {
        // 获取用户初始密码参数
        String userInitPassword = this.serverProperties.getUserInitPassword();
        String initPassword = userInitPassword==null?USER_INIT_PASSWORD:userInitPassword;
        // 获取要重置的用户
        User user = this.getById(id);
        if (user == null) {
            throw new ServiceException("要重置密码的用户不存在或已被删除");
        }
        // 加密密码
        String encryptedInitPassword = PasswordEncryptor.encrypt(initPassword, serverProperties.getPasswordEncryptSecret(), user.getSalt());
        // 重置密码
        user.setPassword(encryptedInitPassword);
        user.setUpdateTime(new Date());
        user.setUpdateUser(getLoginName());
        this.userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    @Transactional
    public void updateSelf(UserDTO dto) {
        // 获取用户
        Long loginUid = getLoginUid();
        User user = this.getById(loginUid);
        if (user == null) {
            throw new ServiceException("您的账号不存在或已被删除");
        }
        // 判断手机号是否已被使用
        if (!dto.getPhoneNumber().equalsIgnoreCase(user.getPhoneNumber())) {
            long phoneCount = this.userMapper.countByPhoneNumber(dto.getPhoneNumber());
            if (phoneCount > 0) {
                throw new ServiceException("手机号已被注册");
            }
        }
        // 修改资料
        BeanUtils.copyProperties(dto, user, "id", "username");
        user.setUpdateTime(new Date());
        user.setUpdateUser(getLoginName());
        this.userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    @Transactional
    public void updatePassword(ModifyPasswordDTO dto) {
        // 获取用户
        Long loginUid = getLoginUid();
        User user = this.getById(loginUid);
        if (user == null) {
            throw new ServiceException("您的账号不存在或已被删除");
        }
        // 校验密码是否合规
        boolean matched =PasswordEncryptor.match(user.getPassword(), dto.getOldPassword(), this.serverProperties.getPasswordEncryptSecret(), user.getSalt());
        if (!matched) {
            throw new ServiceException("原始密码错误");
        }
        // 加密密码
        String encryptedNewPassword = PasswordEncryptor.encrypt(dto.getNewPassword(), serverProperties.getPasswordEncryptSecret(), user.getSalt());
        // 更新密码
        user.setPassword(encryptedNewPassword);
        user.setUpdateTime(new Date());
        user.setUpdateUser(getLoginName());
        this.userMapper.updateByPrimaryKeySelective(user);
    }
}
