package com.ddadjee.iam.domain;

import java.util.Objects;

import static com.ddadjee.common.constants.RegexConstants.*;
public final class Username {
    private final String value;

    public Username(String value) {
        if(value==null || value.isBlank()){
            throw new IllegalArgumentException("username required");
        }
        if(!USERNAME_PATTERN.matcher(value).matches()){
            throw new IllegalArgumentException("invalid username");
        }
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Username username)) return false;
        return value.equals(username.value);
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
