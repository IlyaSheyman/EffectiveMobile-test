package effective.mobile.test.constants;

import java.util.List;

public interface Constants {
    String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    enum Role {
        ROLE_USER,
        ROLE_ADMIN
    }

    List<String> permittedEmails = List.of(
            "admin@gmail.com",
            "jondoe@gmail.com"
    );
}
