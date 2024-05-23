package effective.mobile.test.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import effective.mobile.test.config.validation.password.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

import static effective.mobile.test.constants.Constants.*;

@Data
@Schema(description = "Sign up request")
public class SignUpRequest {

    @NotBlank(message = "Name shouldn't be blank")
    @Pattern(regexp = NAME_PATTERN,
            message = "Name must consist of three words separated by spaces. Example: John Doe Smith")
    private String name;

    @Schema(description = "Login", example = "admin12")
    @Size(min = MIN_LOGIN_LENGTH, max = MAX_LOGIN_LENGTH)
    @NotBlank(message = "Login shouldn't be blank")
    @Pattern(regexp = USERNAME_PATTERN, message = "Login should not contain spaces")
    private String login;

    @Schema(description = "User's email addresses")
    @Size(min = MIN_EMAILS_NUMBER, message = "At least one email address should be provided")
    private List<@Email(message = "Email address should be formatted as user@example.com") String> emails;

    @Schema(description = "Password", example = "UsersPassword12!")
    @Size(min = MIN_USER_PASSWORD_LENGTH, max = MAX_USER_PASSWORD_LENGTH)
    @ValidPassword
    private String password;

    @Size(min = MIN_PHONES_NUMBER, message = "At least one phone number should be provided")
    private List<
            @Size(min = MIN_PHONE_LENGTH,
                    max = MAX_PHONE_LENGTH,
                    message = "Invalid phone number. It it should be 5-20 characters long")
            @Pattern(regexp = PHONE_PATTERN,
                    message = "Invalid phone number. Example: +1234567890 or (123) 456-7890")
                    String> phones;

    @NotNull
    @JsonProperty("birth_date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthDate;
}