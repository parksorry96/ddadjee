package com.ddadjee.common.validation;

import com.ddadjee.common.annotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * {@link ValidPassword} 검증기.
 * <p>
 * 길이 및 대문자/소문자/숫자/특수문자 포함 규칙을 체크합니다.
 */
public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private int min;
    private int max;
    private boolean requireUppercase;
    private boolean requireLowercase;
    private boolean requireDigit;
    private boolean requireSpecial;

    private static final Pattern UPPER = Pattern.compile(".*[A-Z].*");
    private static final Pattern LOWER = Pattern.compile(".*[a-z].*");
    private static final Pattern DIGIT = Pattern.compile(".*[0-9].*");
    // 영문/숫자를 제외한 임의의 특수문자 1개 이상
    private static final Pattern SPECIAL = Pattern.compile(".*[^A-Za-z0-9].*");

    @Override
    public void initialize(ValidPassword anno) {
        this.min = anno.min();
        this.max = anno.max();
        this.requireUppercase = anno.requireUppercase();
        this.requireLowercase = anno.requireLowercase();
        this.requireDigit = anno.requireDigit();
        this.requireSpecial = anno.requireSpecial();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true; // null 처리 여부는 @NotBlank 등과 조합
        int len = value.length();
        if (len < min || len > max) return false;
        if (requireUppercase && !UPPER.matcher(value).matches()) return false;
        if (requireLowercase && !LOWER.matcher(value).matches()) return false;
        if (requireDigit && !DIGIT.matcher(value).matches()) return false;
        if (requireSpecial && !SPECIAL.matcher(value).matches()) return false;
        return true;
    }
}
