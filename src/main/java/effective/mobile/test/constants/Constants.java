package effective.mobile.test.constants;

public interface Constants {
    int MIN_LOGIN_LENGTH = 3;
    int MAX_LOGIN_LENGTH = 20;
    int MIN_USER_PASSWORD_LENGTH = 3;
    int MAX_USER_PASSWORD_LENGTH = 20;
    int MIN_PHONE_LENGTH = 5;
    int MAX_PHONE_LENGTH = 20;

    int MIN_EMAILS_NUMBER = 1;
    int MIN_PHONES_NUMBER = 1;

    double MAX_BALANCE_INDEX = 2.07;
    double INCOME_INDEX = 1.05;

    int TOKEN_EXPIRATION = 100000 * 60 * 24;

    String USERNAME_PATTERN = "^(?=\\S+$).*$";
    String PHONE_PATTERN = "\\+?[0-9. ()-]+";
    String NAME_PATTERN = "^\\S+ \\S+ \\S+$";
    String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    enum Filter {
        BIRTH_DATE, // Filter by birth date
        PHONE,      // Filter by phone
        NAME,       // Filter by name
        EMAIL       // Filter by email
    }
}
