package effective.mobile.test.user.auth.service;

import effective.mobile.test.constants.Constants;
import effective.mobile.test.user.auth.dto.JwtAuthenticationResponse;
import effective.mobile.test.user.auth.dto.SignInRequest;
import effective.mobile.test.user.auth.dto.SignUpRequest;
import effective.mobile.test.user.entity.User;
import effective.mobile.test.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user.
     *
     * @param request the user registration details
     * @return a response containing the generated JWT token
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Constants.Role.ROLE_USER)
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Authenticates a user.
     *
     * @param request the user authentication details
     * @return a response containing the generated JWT token
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        var user = userService
                .getByEmail(request.getEmail());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                request.getPassword()
        ));

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
