package effective.mobile.test.task.dto;

import effective.mobile.test.constants.Priority;
import effective.mobile.test.constants.Status;
import effective.mobile.test.user.dto.UserTaskDto;

import java.util.List;

public class TaskCreatedDto {
    private int id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private UserTaskDto author;
    private UserTaskDto assignee;
    private List<Comment> comments;

}
