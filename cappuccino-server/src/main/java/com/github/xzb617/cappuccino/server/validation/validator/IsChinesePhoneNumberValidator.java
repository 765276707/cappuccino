package com.github.xzb617.cappuccino.server.validation.validator;

import com.github.xzb617.cappuccino.server.validation.annotation.IsChinesePhoneNumber;
import com.github.xzb617.cappuccino.server.validation.regex.PatternPool;
import com.github.xzb617.cappuccino.server.validation.regex.RegexMatcher;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsChinesePhoneNumberValidator implements ConstraintValidator<IsChinesePhoneNumber, String> {

    @Override
    public boolean isValid(String content, ConstraintValidatorContext constraintValidatorContext) {
        if (content==null || "".equals(content)) {
            return true;
        }
        return RegexMatcher.isMatch(PatternPool.CHINESE_PHONE_NUMBER, content);
    }

}
