package effective.mobile.test.task.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import effective.mobile.test.constants.Priority;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static effective.mobile.test.constants.Constants.*;

@Data
public class TaskCreateRequest {
    @NotNull(message = "Title cannot be null")
    @Size(min = MIN_TITLE_LENGTH,
            max = MAX_TITLE_LENGTH,
            message = "Title must be between " + MIN_TITLE_LENGTH +
                    " and " + MAX_TITLE_LENGTH + " characters")
    private String title;

    @NotNull(message = "Description cannot be null")
    @Size(min = MIN_DESCRIPTION_LENGTH,
            max = MAX_DESCRIPTION_LENGTH,
            message = "Description must be between " + MIN_DESCRIPTION_LENGTH +
                    " and " + MAX_DESCRIPTION_LENGTH + " characters")
    private String description;

    @NotNull(message = "Priority cannot be null")
    private Priority priority;

    @JsonProperty("assignee_id")
    @NotNull(message = "Assignee ID cannot be null")
    private Integer assigneeId;
}
