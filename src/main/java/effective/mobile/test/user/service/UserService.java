package effective.mobile.test.user.service;

import effective.mobile.test.constants.Constants;
import effective.mobile.test.exceptions.model.ConflictRequestException;
import effective.mobile.test.exceptions.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import effective.mobile.test.user.auth.dto.GetAdminRequest;
import effective.mobile.test.user.entity.User;
import effective.mobile.test.user.storage.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    @Value("${admin.secret.code}")
    private String adminSecretCode;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return repository.save(user);
    }

    /**
     * Создание пользователя
     *
     * @return созданный пользователь
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
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    /**
     * Выдача прав администратора текущему пользователю
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

    public User getByEmail(String email) {
        Optional<User> user = repository.findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException("Пользователь с таким email не найден");
        }
    }

    /**
     * Getting effective.mobile.test.user by id
     *
     * @return effective.mobile.test.user
     */
    public User getUserById(int userId) {
        return repository
                .findById(userId)
                .orElseThrow(()-> new NotFoundException(String.format("User with id %d not found", userId)));
    }

}
