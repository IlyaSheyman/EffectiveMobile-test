package effective.mobile.test.user.dto.user;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserUpdateDto {
    private int id;
    private String name;
    private String login;
    private List<String> emails;
    private List<String> phones;
    private LocalDate birthDate;
}
