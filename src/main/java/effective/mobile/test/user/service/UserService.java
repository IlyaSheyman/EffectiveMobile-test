package effective.mobile.test.user.service;

import effective.mobile.test.constants.Constants;
import effective.mobile.test.exceptions.model.ConflictRequestException;
import effective.mobile.test.exceptions.model.NotFoundException;
import effective.mobile.test.user.auth.dto.GetAdminRequest;
import effective.mobile.test.user.entity.User;
import effective.mobile.test.user.storage.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    @Value("${admin.secret.code}")
    private String adminSecretCode;

    /**
     * Saves a user to the repository.
     *
     * @param user the user to save
     * @return the saved user
     */
    public User save(User user) {
        return repository.save(user);
    }

    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @return the created user
     * @throws ConflictRequestException if a user with the same username or email already exists
     */
    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new ConflictRequestException("Пользователь с таким именем уже существует");
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new ConflictRequestException("Пользователь с таким email уже существует");
        }

        return save(user);
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user
     * @return the user
     * @throws UsernameNotFoundException if the user is not found
     */
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    /**
     * Retrieves a user by their username.
     * <p>
     * This method is required for Spring Security.
     *
     * @return the user as a UserDetailsService
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Retrieves the currently authenticated user.
     *
     * @return the current user
     */
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    /**
     * Grants administrator privileges to the current user.
     *
     * @param request the request containing the secret code and email for admin access
     * @return a message indicating whether the operation was successful
     */
    public String getAdmin(GetAdminRequest request) {
        if (request.getSecretCode().equals(adminSecretCode) &&
                Constants.permittedEmails.contains(request.getEmail())) {

            var user = getCurrentUser();
            user.setRole(Constants.Role.ROLE_ADMIN);
            save(user);

            return "User promoted to admin successfully.";
        }

        return "Unauthorized access to admin functionality.";
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email of the user
     * @return the user
     * @throws NotFoundException if the user is not found
     */
    public User getByEmail(String email) {
        Optional<User> user = repository.findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException("Пользователь с таким email не найден");
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user
     * @return the user
     * @throws NotFoundException if the user is not found
     */
    public User getUserById(int userId) {
        return repository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d not found", userId)));
    }

}
