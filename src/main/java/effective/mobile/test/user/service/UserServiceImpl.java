package effective.mobile.test.user.service;

import effective.mobile.test.exception.model.BadRequestException;
import effective.mobile.test.exception.model.NotFoundException;
import effective.mobile.test.user.entity.User;
import effective.mobile.test.user.storage.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository repository;

    /**
     * Saving user
     *
     * @return сохраненный пользователь
     */
    @Override
    public User save(User user) {
        return repository.save(user);
    }

    /**
     * Creating user
     *
     * @return created user
     */
    @Override
    public User create(User user) {
        String login = user.getLogin();
        List<String> emails = user.getEmails();

        if (repository.existsByLoginIgnoreCase(login)) {
            throw new BadRequestException("User with this login already exists");
        }

        for (String email : emails) {
            if (repository.existsUserByEmailsContains(email)) {
                throw new BadRequestException("User with email " + email + " already exists");
            }
        }

        return save(user);
    }

    /**
     * Getting user by id
     *
     * @return user
     */
    @Override
    public User getUserById(int userId) {
        return repository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    /**
     * Retrieves a user by username.
     * <p>
     * Needed for Spring Security.
     *
     * @return the user
     */
    @Override
    public UserDetailsService userDetailsService() {
        return this::getByLogin;
    }

    /**
     * Getting user by login
     *
     * @return user
     */
    @Override
    public User getByLogin(String login) {
        User user = repository.getByLogin(login);
        if (user != null) {
            return user;
        } else {
            throw new NotFoundException("User with login " + login + " not found");
        }
    }
}
