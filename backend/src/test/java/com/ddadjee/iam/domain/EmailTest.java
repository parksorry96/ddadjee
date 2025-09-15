package com.ddadjee.iam.domain;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {

    @ParameterizedTest
    @ValueSource(strings={
            "bad",
            "user@",
            "@example.com",
            "user@example",
            "user@.com",
            "user@exa_mple.com"
    })
    void invalidFormatShouldThrow(String input){
        assertThrows(IllegalArgumentException.class, () -> new Email(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "user@example.com",
            "user.name+tag@sub.example.co"
    })
    void validFormatShouldHoldValue(String input) {
        Email email = new Email(input);
        assertEquals(input, email.getValue());
    }
}
