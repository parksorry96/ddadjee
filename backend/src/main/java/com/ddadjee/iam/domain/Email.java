package com.ddadjee.iam.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import static com.ddadjee.common.constants.RegexConstants.EMAIL_PATTERN;

@Getter
@EqualsAndHashCode
public final class Email {

    private final String value;

    public Email(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("email required");
        }
        if(!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("invalid email");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
