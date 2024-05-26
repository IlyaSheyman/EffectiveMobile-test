package effective.mobile.test.user.dto.user;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserSearchDto {
    private String name;
    private List<String> emails;
    private List<String> phones;
    private LocalDate birthDate;
}
