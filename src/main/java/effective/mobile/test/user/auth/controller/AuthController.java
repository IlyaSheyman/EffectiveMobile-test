package effective.mobile.test.user.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import effective.mobile.test.user.auth.dto.GetAdminRequest;
import effective.mobile.test.user.auth.dto.JwtAuthenticationResponse;
import effective.mobile.test.user.auth.dto.SignInRequest;
import effective.mobile.test.user.auth.dto.SignUpRequest;
import effective.mobile.test.user.auth.service.AuthenticationService;
import effective.mobile.test.user.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @GetMapping("/get-admin")
    @Operation(summary = "Получить роль ADMIN")
    public String getAdmin(@RequestBody GetAdminRequest request) {
        return userService.getAdmin(request);
    }
}
