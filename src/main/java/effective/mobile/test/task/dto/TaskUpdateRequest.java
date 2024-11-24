package effective.mobile.test.task.dto;

import effective.mobile.test.constants.Priority;
import effective.mobile.test.constants.Status;
import effective.mobile.test.user.entity.User;
import jakarta.persistence.*;

import java.util.List;

public class TaskUpdateRequest {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private User assignee;
}
