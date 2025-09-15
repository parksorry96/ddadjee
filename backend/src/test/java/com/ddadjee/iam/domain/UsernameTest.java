package com.ddadjee.iam.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class UsernameTest {

    @ParameterizedTest
    @ValueSource(strings={
            "ab",
            "user!",
            "한글닉네임",
            "space user"
    })
    void invalidUsernameShouldThrow(String input){
        assertThrows(IllegalArgumentException.class, () -> new Username(input));
    }

    @Test
    void longerThan32ShouldThrow(){
        String tooLong = "a".repeat(33);
        assertThrows(IllegalArgumentException.class, () -> new Username(tooLong));
    }

    @ParameterizedTest
    @ValueSource(strings ={
            "user123",
            "user_name",
            "User-123"
    })
    void validUsernameShouldHoldValue(String input){
        Username username = new Username(input);
        assertEquals(input, username.getValue());
    }

}
