package effective.mobile.test.user.service;

import effective.mobile.test.constants.Constants;
import effective.mobile.test.user.dto.data.EmailDto;
import effective.mobile.test.user.dto.data.PhoneDto;
import effective.mobile.test.user.dto.user.UserSearchDto;
import effective.mobile.test.user.dto.user.UserUpdateDto;
import effective.mobile.test.user.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    User save(User user);

    User create(User user);

    List<UserSearchDto> findUsers(Constants.Filter filter,
                                  String filterValue,
                                  int page,
                                  int size);

    User getUserById(int userId);

    UserDetailsService userDetailsService();

    User getByLogin(String login);

    UserUpdateDto addPhoneNumber(String token, PhoneDto dto);

    void deletePhoneNumber(String token, PhoneDto dto);

    UserUpdateDto addEmail(String token, EmailDto dto);

    void deleteEmail(String token, EmailDto dto);
}
