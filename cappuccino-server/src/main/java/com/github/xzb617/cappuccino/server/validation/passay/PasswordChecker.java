package com.github.xzb617.cappuccino.server.validation.passay;

import org.passay.LengthComplexityRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.Rule;

import javax.validation.ValidationException;
import java.util.List;

/**
 * 密码校验器
 * @author xzb617
 */
public class PasswordChecker {

    // 默认提示信息
    private static final String DEFAULT_ERROR_MESSAGE = "Your password strength does not matched.";

    /**
     * 校验是否符合密码规则
     * @param password 密码
     * @param errorMessage 错误提示
     */
    public static void valid(String password, PasswordComplexity complexity, String errorMessage) {
        List<Rule> rules = PasswordRule.getRules(complexity);
        PasswordValidator passwordValidator = new PasswordValidator(rules);
        // 返回校验结果
        valid(passwordValidator, password, errorMessage);
    }


    /**
     * 校验结果
     * @param passwordValidator 密码校验器
     * @param password 密码
     * @param errorMessage 错误提示
     */
    public static void valid(PasswordValidator passwordValidator, String password, String errorMessage) {
        // 返回校验结果
        if (!valid(passwordValidator, password)) {
            throw new ValidationException(errorMessage==null?DEFAULT_ERROR_MESSAGE:errorMessage);
        }
    }

    /**
     * 校验是否匹配
     * @param passwordValidator 密码校验器
     * @param password 密码
     * @return boolean
     */
    private static boolean valid(PasswordValidator passwordValidator, String password) {
        return passwordValidator.validate(new PasswordData(password)).isValid();
    }

}
