package effective.mobile.test.task.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import effective.mobile.test.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entity representing a comment on a task")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the comment", example = "101")
    private Integer id;

    @Schema(description = "Text content of the comment", example = "This is a comment on the task.")
    private String text;

    @ManyToOne
    @Schema(description = "Task associated with this comment")
    private Task task;

    @ManyToOne
    @Schema(description = "Author of the comment")
    private User author;

    @JsonProperty("created_at")
    @Schema(description = "Timestamp when the comment was created")
    private LocalDateTime createdAt;
}