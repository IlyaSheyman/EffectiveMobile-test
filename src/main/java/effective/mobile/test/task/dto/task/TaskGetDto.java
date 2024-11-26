package effective.mobile.test.task.dto.task;

import effective.mobile.test.constants.Priority;
import effective.mobile.test.constants.Status;
import effective.mobile.test.task.dto.comment.CommentDto;
import effective.mobile.test.user.dto.UserTaskDto;
import lombok.Data;

import java.util.List;

@Data
public class TaskGetDto {
    private int id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private UserTaskDto author;
    private UserTaskDto assignee;
    private List<CommentDto> comments;
}
