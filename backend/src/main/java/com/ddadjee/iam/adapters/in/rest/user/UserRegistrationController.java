package com.ddadjee.iam.adapters.in.rest.user;

import com.ddadjee.common.web.ApiResponse;
import com.ddadjee.iam.application.port.in.RegisterUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.ddadjee.iam.adapters.in.rest.user.RegisterUserRestMapper.toCommand;
import static com.ddadjee.iam.adapters.in.rest.user.RegisterUserRestMapper.toResponse;

/**
 * 회원가입 REST 컨트롤러.
 */
@RestController
@RequestMapping(UserApiRoutes.USERS_BASE_PATH)
@RequiredArgsConstructor
@Tag(name = "IAM Users")
public class UserRegistrationController {

    private final RegisterUserUseCase registerUserUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "회원가입")
    public ApiResponse<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        var savedUser = registerUserUseCase.register(toCommand(request));
        return ApiResponse.ok(toResponse(savedUser), "회원가입이 완료되었습니다.");
    }
}
