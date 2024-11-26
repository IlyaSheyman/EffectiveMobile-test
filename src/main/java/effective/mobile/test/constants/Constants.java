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

    int MIN_TITLE_LENGTH = 5;
    int MAX_TITLE_LENGTH = 100;
    int MIN_DESCRIPTION_LENGTH = 5;
    int MAX_DESCRIPTION_LENGTH = 255;
    int MIN_COMMENT_LENGTH = 5;
    int MAX_COMMENT_LENGTH = 255;
}
