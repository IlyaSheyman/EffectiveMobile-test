package effective.mobile.test.user.dto.user;

import effective.mobile.test.account.dto.account.AccountSmallDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserAccountDto {
    private String name;
    private String login;
    private List<String> emails;
    private List<String> phones;
    private LocalDate birthDate;
    private AccountSmallDto account;
}
