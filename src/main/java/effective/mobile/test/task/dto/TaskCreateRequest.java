package effective.mobile.test.task.dto;

import effective.mobile.test.constants.Priority;
import effective.mobile.test.constants.Status;
import effective.mobile.test.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class TaskCreateRequest {
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @ManyToOne
    private User assignee;
}
