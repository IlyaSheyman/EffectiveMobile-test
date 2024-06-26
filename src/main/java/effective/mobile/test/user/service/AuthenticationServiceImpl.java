package effective.mobile.test.user.service;

import effective.mobile.test.account.entity.Account;
import effective.mobile.test.config.security.JwtAuthenticationResponse;
import effective.mobile.test.config.security.JwtService;
import effective.mobile.test.user.dto.request.SignInRequest;
import effective.mobile.test.user.dto.request.SignUpRequest;
import effective.mobile.test.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    /**
     * User registration
     *
     * @param request user's data
     * @return token
     */
    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        var user = User.builder()
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .phones(request.getPhones())
                .login(request.getLogin().toLowerCase())
                .emails(request.getEmails())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        Double initialDeposit = request.getInitialDeposit();
        Account account = Account.builder()
                .initialDeposit(initialDeposit)
                .balance(initialDeposit)
                .build();

        user.setAccount(account);

        userService.create(user);

        var jwt = jwtService.generateToken(user);

        log.info(String.format("user with login %s is created with initial deposit %.2f",
                user.getLogin(),
                initialDeposit));
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * User authentication
     *
     * @param request user's data
     * @return token
     */
    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        User user = userService.getByLogin(request.getLogin());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(),
                request.getPassword()
        ));

        var jwt = jwtService.generateToken(user);

        log.info(String.format("user with id %d signed in", user.getId()));
        return new JwtAuthenticationResponse(jwt);
    }
}