package com.github.xzb617.cappuccino.server.validation.passay;

import org.passay.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PasswordRule {

    /**
     * 获取校验规则
     * @return
     */
    public static List<Rule> getRules(PasswordComplexity complexity) {
        List<Rule> rules;
        switch (complexity) {
            case SIMPLE:
                rules = Arrays.asList(
                        new LengthRule(8, 20),
                        // 不允许连续6个字母字符
                        new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 4, false),
                        // 不允许连续6个数字字符
                        new IllegalSequenceRule(EnglishSequenceData.Numerical, 4, false),
                        // 不允许有空白字符
                        new WhitespaceRule()
                );
                break;

            case MEDIUM:
                rules = Arrays.asList(
                        new LengthRule(8, 20),
                        // 至少包含一个字母，一个数字
                        new CharacterRule(EnglishCharacterData.Alphabetical, 1),
                        new CharacterRule(EnglishCharacterData.Digit, 1),
                        // 不允许连续6个字母字符
                        new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 6, false),
                        // 不允许连续6个数字字符
                        new IllegalSequenceRule(EnglishSequenceData.Numerical, 6, false),
                        // 不允许有空白字符
                        new WhitespaceRule()
                );
                break;

            case COMPLEX:
                rules = Arrays.asList(
                        new LengthRule(8, 20),
                        // 至少包含一个大写，一个小写，一个数字，一个特殊字符
                        new CharacterRule(EnglishCharacterData.UpperCase, 1),
                        new CharacterRule(EnglishCharacterData.LowerCase, 1),
                        new CharacterRule(EnglishCharacterData.Digit, 1),
                        new CharacterRule(EnglishCharacterData.Special, 1),
                        // 不允许连续8个字母字符
                        new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 8, false),
                        // 不允许连续8个数字字符
                        new IllegalSequenceRule(EnglishSequenceData.Numerical, 8, false),
                        // 不允许连续8个键盘字符
                        new IllegalSequenceRule(EnglishSequenceData.USQwerty, 8, false),
                        // 不允许有空白字符
                        new WhitespaceRule()
                );
                break;

            default:
                rules = Collections.emptyList();
        }
        return rules;
    }

}
