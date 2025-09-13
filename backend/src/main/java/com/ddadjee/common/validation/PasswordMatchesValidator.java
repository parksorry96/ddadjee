package com.ddadjee.common.validation;

import com.ddadjee.common.annotation.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

/**
 * {@link PasswordMatches} 검증기.
 * <p>
 * 리플렉션으로 지정된 두 필드 값을 읽어 동일성(equals) 검증을 수행합니다.
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    private String first;
    private String second;

    @Override
    public void initialize(PasswordMatches anno) {
        this.first = anno.first();
        this.second = anno.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true; // null 케이스는 @NotNull로 별도 처리
        try {
            Object a = readField(value, first);
            Object b = readField(value, second);
            if (a == null || b == null) return false;
            return a.equals(b);
        } catch (Exception e) {
            return false;
        }
    }

    private Object readField(Object target, String name) throws Exception {
        Field f = findField(target.getClass(), name);
        f.setAccessible(true);
        return f.get(target);
    }

    private Field findField(Class<?> type, String name) throws NoSuchFieldException {
        Class<?> t = type;
        while (t != null) {
            try { return t.getDeclaredField(name); }
            catch (NoSuchFieldException ignored) { t = t.getSuperclass(); }
        }
        throw new NoSuchFieldException(name);
    }
}

