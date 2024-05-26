package effective.mobile.test.user.service;

import effective.mobile.test.config.security.JwtService;
import effective.mobile.test.constants.Constants;
import effective.mobile.test.exception.model.BadRequestException;
import effective.mobile.test.exception.model.NotFoundException;
import effective.mobile.test.user.dto.data.EmailDto;
import effective.mobile.test.user.dto.data.PhoneDto;
import effective.mobile.test.user.dto.user.UserSearchDto;
import effective.mobile.test.user.dto.user.UserUpdateDto;
import effective.mobile.test.user.entity.User;
import effective.mobile.test.user.mapper.UserMapper;
import effective.mobile.test.user.storage.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Stream;

import static effective.mobile.test.constants.Constants.PHONE_PATTERN;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository repository;
    private final JwtService jwtService;

    private final UserMapper mapper;

    /**
     * Creating user
     *
     * @return created user
     */
    @Override
    public User create(User user) {
        String login = user.getLogin();
        List<String> emails = user.getEmails();
        List<String> phones = user.getPhones();

        if (repository.existsByLoginIgnoreCase(login)) {
            throw new BadRequestException("User with this login already exists");
        }

        for (String email : emails) {
            if (repository.existsUserByEmailsContains(email)) {
                throw new BadRequestException("User with email " + email + " already exists");
            }
        }

        for (String phone : phones) {
            if (repository.existsByPhonesContaining(phone)) {
                throw new BadRequestException("User with phone " + phone + " already exists");
            }
        }
        return save(user);
    }

    @Override
    public UserUpdateDto addPhoneNumber(String token, PhoneDto dto) {
        User user = extractUserFromToken(token);

        if (repository.existsByPhonesContaining(dto.getPhone())) {
            throw new BadRequestException("Phone number is already in use");
        }

        user.getPhones().add(dto.getPhone());
        repository.save(user);

        log.info(String.format("user with id %d deleted phone %s", user.getId(), dto.getPhone()));
        return mapper.toUserUpdateDto(user);
    }

    @Override
    public void deletePhoneNumber(String token, PhoneDto dto) {
        User user = extractUserFromToken(token);
        List<String> phones = user.getPhones();

        String numberToDelete = dto.getPhone();
        String lastNumber = phones.get(phones.size() - 1);

        if (phones.contains(numberToDelete)) {
            if (!numberToDelete.equals(lastNumber)) {
                phones.remove(numberToDelete);
                user.setPhones(phones);
                repository.save(user);
            } else {
                throw new BadRequestException("Number you delete should not be last");
            }
        } else {
            throw new BadRequestException("There is no such a number in user's phones");
        }

        log.info(String.format("user with id %d deleted phone %s", user.getId(), dto.getPhone()));
    }

    @Override
    public UserUpdateDto addEmail(String token, EmailDto dto) {
        User user = extractUserFromToken(token);

        if (repository.existsUserByEmailsContains(dto.getEmail())) {
            throw new BadRequestException("Email is already in use");
        }

        user.getEmails().add(dto.getEmail());
        repository.save(user);

        log.info(String.format("user with id %d added email %s", user.getId(), dto.getEmail()));

        return mapper.toUserUpdateDto(user);
    }

    @Override
    public void deleteEmail(String token, EmailDto dto) {
        User user = extractUserFromToken(token);
        List<String> emails = user.getEmails();

        String emailToDelete = dto.getEmail();
        String lastEmail = emails.get(emails.size() - 1);

        if (emails.contains(emailToDelete)) {
            if (!emailToDelete.equals(lastEmail)) {
                emails.remove(emailToDelete);
                user.setEmails(emails);
                repository.save(user);
            } else {
                throw new BadRequestException("Email you delete should not be last");
            }
        } else {
            throw new BadRequestException("There is no such an email in user's emails list");
        }

        log.info(String.format("user with id %d deleted email %s", user.getId(), dto.getEmail()));
    }

    @Override
    public List<UserSearchDto> findUsers(Constants.Filter filter,
                                         String filterValue,
                                         int page,
                                         int size) {
        validateFilterValue(filter, filterValue);

        List<User> users = repository.findAll();
        Stream<User> userStream = users.stream();

        userStream = switch (filter) {
            case BIRTH_DATE -> {
                LocalDate birthDate = LocalDate.parse(filterValue);
                yield userStream.filter(user -> user.getBirthDate().isAfter(birthDate));
            }
            case PHONE -> userStream.filter(user -> user.getPhones().contains(filterValue));
            case NAME -> userStream.filter(user -> user.getName().startsWith(filterValue));
            case EMAIL -> userStream.filter(user -> user.getEmails().contains(filterValue));
        };

        List<User> filteredUsers = userStream
                .skip((long) page * size)
                .limit(size)
                .toList();

        return filteredUsers.stream()
                .map(mapper::toUserSearchDto)
                .toList();
    }

    private void validateFilterValue(Constants.Filter filter, String filterValue) {
        switch (filter) {
            case BIRTH_DATE:
                try {
                    LocalDate.parse(filterValue);
                } catch (DateTimeParseException e) {
                    throw new BadRequestException("Invalid date format. Expected format: yyyy-MM-dd");
                }
                break;
            case PHONE:
                if (!filterValue.matches(PHONE_PATTERN)) {
                    throw new BadRequestException("Invalid phone number format. Expected format: +1234567890");
                }
                break;
            case NAME:
                if (filterValue == null || filterValue.isBlank()) {
                    throw new BadRequestException("Name cannot be null or empty.");
                }
                break;
            case EMAIL:
                if (!filterValue.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    throw new BadRequestException("Invalid email format.");
                }
                break;
            default:
                throw new BadRequestException("Unknown filter type.");
        }
    }

    private User extractUserFromToken(String userToken) {
        userToken = userToken.substring(7);
        return getUserById(jwtService.extractUserId(userToken));
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


    /**
     * Saving user
     *
     * @return сохраненный пользователь
     */
    @Override
    public User save(User user) {
        return repository.save(user);
    }
}
