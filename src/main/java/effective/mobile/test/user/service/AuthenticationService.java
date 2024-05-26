package effective.mobile.test.user.service;

import effective.mobile.test.config.security.JwtAuthenticationResponse;
import effective.mobile.test.user.dto.request.SignInRequest;
import effective.mobile.test.user.dto.request.SignUpRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest request);
    JwtAuthenticationResponse signIn(SignInRequest request);
}
