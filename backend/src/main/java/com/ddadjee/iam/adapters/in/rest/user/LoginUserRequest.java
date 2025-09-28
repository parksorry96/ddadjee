package com.ddadjee.iam.adapters.in.rest.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import static com.ddadjee.common.constants.MessageKeys.VALIDATION_EMAIL;
import static com.ddadjee.common.constants.MessageKeys.VALIDATION_NOT_BLANK;

/**
 * 로그인 REST 요청 DTO.
 */
public record LoginUserRequest(
        @NotBlank(message = "{" + VALIDATION_NOT_BLANK + "}")
        @Email(message = "{" + VALIDATION_EMAIL + "}")
        String email,

        @NotBlank(message = "{" + VALIDATION_NOT_BLANK + "}")
        String password
) {
}
