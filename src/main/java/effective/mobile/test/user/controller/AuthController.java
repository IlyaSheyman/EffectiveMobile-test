package effective.mobile.test.user.controller;

import effective.mobile.test.config.security.JwtAuthenticationResponse;
import effective.mobile.test.user.dto.SignInRequest;
import effective.mobile.test.user.dto.SignUpRequest;
import effective.mobile.test.user.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "Public controller")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "User's registration")
    @PostMapping("/sign-up")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        log.info(String.format("sign-up user with login %s", request.getLogin()));
        return authenticationService.signUp(request);
    }

    @Operation(summary = "User's authorization")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        log.info(String.format("sign-in user with login %s", request.getLogin()));
        return authenticationService.signIn(request);
    }
}
