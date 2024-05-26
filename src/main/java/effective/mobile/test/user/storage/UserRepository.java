package effective.mobile.test.user.storage;

import effective.mobile.test.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByLoginIgnoreCase(String username);
    User getByLogin(String login);
    boolean existsUserByEmailsContains(String email);
    boolean existsByPhonesContaining(String phone);
    Optional<User> findUserByEmailsContains(String email);
    Optional<User> findUserByPhonesContains(String recipientNumber);
}
