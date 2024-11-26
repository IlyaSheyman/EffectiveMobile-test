package effective.mobile.test.task.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import effective.mobile.test.constants.Priority;
import effective.mobile.test.constants.Status;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import static effective.mobile.test.constants.Constants.*;

@Data
@Builder
public class TaskUpdateRequest {
    private int id;

    @Size(min = MIN_TITLE_LENGTH,
            max = MAX_TITLE_LENGTH,
            message = "Title must be between " + MIN_TITLE_LENGTH +
                    " and " + MAX_TITLE_LENGTH + " characters")
    private String title;

    @Size(min = MIN_DESCRIPTION_LENGTH,
            max = MAX_DESCRIPTION_LENGTH,
            message = "Description must be between " + MIN_DESCRIPTION_LENGTH +
                    " and " + MAX_DESCRIPTION_LENGTH + " characters")
    private String description;

    private Status status;

    private Priority priority;

    @JsonProperty("assignee_id")
    private Integer assigneeId;
}
