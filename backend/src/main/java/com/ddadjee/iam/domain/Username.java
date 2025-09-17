package com.ddadjee.iam.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import static com.ddadjee.common.constants.RegexConstants.*;

@Getter
@EqualsAndHashCode
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
    @Override
    public String toString() {
        return value;
    }
}
