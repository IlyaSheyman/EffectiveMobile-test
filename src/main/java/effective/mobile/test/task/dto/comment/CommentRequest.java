package effective.mobile.test.task.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static effective.mobile.test.constants.Constants.MAX_COMMENT_LENGTH;
import static effective.mobile.test.constants.Constants.MIN_COMMENT_LENGTH;

@Data
public class CommentRequest {
    @NotNull(message = "Comment text cannot be null")
    @Size(min = MIN_COMMENT_LENGTH,
            max = MAX_COMMENT_LENGTH,
            message = "Comment must be between " + MIN_COMMENT_LENGTH +
                    " and " + MAX_COMMENT_LENGTH + " characters")
    private String text;

    @JsonProperty("task_id")
    private int taskId;
}
