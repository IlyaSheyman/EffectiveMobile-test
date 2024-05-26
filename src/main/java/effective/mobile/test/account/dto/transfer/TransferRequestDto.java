package effective.mobile.test.account.dto.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static effective.mobile.test.constants.Constants.*;

@Data
public class TransferRequestDto {
    @JsonProperty("number")
    @Size(min = MIN_PHONE_LENGTH,
            max = MAX_PHONE_LENGTH,
            message = "Invalid phone number. It it should be 5-20 characters long")
    private String recipientNumber;

    @JsonProperty("email")
    @Size(min = MIN_EMAILS_NUMBER, message = "At least one email address should be provided")
    @Email(message = "Email address should be formatted as user@example.com")
    private String email;

    @Min(0)
    private Double amount;
}