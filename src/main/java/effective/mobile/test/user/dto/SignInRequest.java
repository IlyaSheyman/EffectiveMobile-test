package effective.mobile.test.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static effective.mobile.test.constants.Constants.*;

@Data
@Schema(description = "Sign in request")
public class SignInRequest {

    @Schema(description = "Login", example = "admin12")
    @Size(min = MIN_LOGIN_LENGTH, max = MAX_LOGIN_LENGTH)
    @NotBlank(message = "Login shouldn't be blank")
    @Pattern(regexp = USERNAME_PATTERN, message = "Login should not contain spaces")
    private String login;

    @Schema(description = "Password", example = "UsersPassword12!")
    @Size(min = MIN_USER_PASSWORD_LENGTH, max = MAX_USER_PASSWORD_LENGTH)
    private String password;

}