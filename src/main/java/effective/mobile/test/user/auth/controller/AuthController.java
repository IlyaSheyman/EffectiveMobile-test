package effective.mobile.test.user.auth.controller;

import effective.mobile.test.user.auth.dto.GetAdminRequest;
import effective.mobile.test.user.auth.dto.JwtAuthenticationResponse;
import effective.mobile.test.user.auth.dto.SignInRequest;
import effective.mobile.test.user.auth.dto.SignUpRequest;
import effective.mobile.test.user.auth.service.AuthenticationService;
import effective.mobile.test.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and role management")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/sign-up")
    @Operation(summary = "Register a new user",
            description = "Allows a user to create an account by providing valid details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully registered",
                    content = @Content(schema = @Schema(implementation = JwtAuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully authenticated",
                    content = @Content(schema = @Schema(implementation = JwtAuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @GetMapping("/get-admin")
    @Operation(summary = "Получить роль ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role granted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public String getAdmin(@RequestBody GetAdminRequest request) {
        return userService.getAdmin(request);
    }
}
