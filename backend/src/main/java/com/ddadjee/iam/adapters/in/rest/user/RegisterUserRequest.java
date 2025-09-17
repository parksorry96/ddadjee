package com.ddadjee.iam.adapters.in.rest.user;

import com.ddadjee.common.annotation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.ddadjee.common.constants.MessageKeys.*;

/**
 * 회원가입 REST 요청 DTO.
 */
public record RegisterUserRequest(
        @NotBlank(message = "{" + VALIDATION_NOT_BLANK + "}")
        @Email(message = "{" + VALIDATION_EMAIL + "}")
        String email,

        @NotBlank(message = "{" + VALIDATION_NOT_BLANK + "}")
        @Size(min = 3, max = 32, message = "{" + VALIDATION_USERNAME + "}")
        String username,

        @NotBlank(message = "{" + VALIDATION_NOT_BLANK + "}")
        @ValidPassword
        String password
) {}
