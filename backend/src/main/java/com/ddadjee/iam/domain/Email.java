package com.ddadjee.iam.domain;

import java.util.Objects;
import static com.ddadjee.common.constants.RegexConstants.EMAIL_PATTERN;

public final class Email {

    public final String value;

    public Email(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("email required");
        }
        if(!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("invalid email");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (! (o instanceof Email email)) return false;
        return value.equals(email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
