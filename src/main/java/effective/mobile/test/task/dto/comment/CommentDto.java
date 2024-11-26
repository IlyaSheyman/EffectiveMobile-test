package effective.mobile.test.task.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private int id;
    private String text;
    @JsonProperty("task_id")
    private Long taskId;
    @JsonProperty("author_username")
    private String authorUsername;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
