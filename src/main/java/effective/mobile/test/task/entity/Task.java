package effective.mobile.test.task.entity;

import effective.mobile.test.constants.Priority;
import effective.mobile.test.constants.Status;
import effective.mobile.test.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tasks")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Entity representing a task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the task",
            example = "1")
    private int id;

    @Schema(description = "Title of the task",
            example = "Implement user authentication")
    private String title;

    @Schema(description = "Detailed description of the task",
            example = "Develop and integrate the user authentication module.")
    private String description;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status of the task",
            example = "IN_PROGRESS")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Priority of the task",
            example = "HIGH")
    private Priority priority;

    @ManyToOne
    @Schema(description = "Author of the task")
    private User author;

    @ManyToOne
    @Schema(description = "Assignee of the task")
    private User assignee;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    @Schema(description = "List of comments associated with the task")
    private List<Comment> comments;

}
