package com.github.xzb617.cappuccino.server.validation.validator;

import com.github.xzb617.cappuccino.server.validation.annotation.IsEmailAddress;
import com.github.xzb617.cappuccino.server.validation.regex.PatternPool;
import com.github.xzb617.cappuccino.server.validation.regex.RegexMatcher;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsEmailAddressValidator implements ConstraintValidator<IsEmailAddress, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s==null || "".equals(s)) {
            return true;
        }
        return RegexMatcher.isMatch(PatternPool.EMAIL, s);
    }

}
