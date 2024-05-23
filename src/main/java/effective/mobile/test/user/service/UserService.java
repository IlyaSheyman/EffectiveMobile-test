package effective.mobile.test.user.service;

import effective.mobile.test.user.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    User save(User user);

    User create(User user);

    User getUserById(int userId);

    UserDetailsService userDetailsService();

    User getByLogin(String login);
}
