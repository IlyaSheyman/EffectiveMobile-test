package effective.mobile.test.task.controller;

import effective.mobile.test.constants.TaskFilter;
import effective.mobile.test.task.dto.comment.CommentDto;
import effective.mobile.test.task.dto.comment.CommentRequest;
import effective.mobile.test.task.dto.task.TaskGetDto;
import effective.mobile.test.task.dto.task.TaskUpdatedDto;
import effective.mobile.test.task.dto.task.UpdateStatusRequest;
import effective.mobile.test.task.service.CommentService;
import effective.mobile.test.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/tasks")
public class TaskPublicController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/my-tasks")
    public List<TaskGetDto> getMyTasks(
            @RequestHeader(name = "Authorization") String userToken,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ALL") String filter) {

        Pageable pageable = PageRequest.of(page, size);
        return taskService.getTasksByAssignee(userToken, pageable, TaskFilter.fromString(filter));
    }


    @PatchMapping("/status")
    @Transactional
    public TaskUpdatedDto updateTaskStatus(@RequestHeader(name = "Authorization") String userToken,
                                           @RequestBody @Valid UpdateStatusRequest statusRequest) {
        return taskService.updateTaskStatus(statusRequest, userToken);
    }

    @PostMapping("/{taskId}/comments")
    @Transactional
    public CommentDto addComment(@RequestHeader(name = "Authorization") String userToken,
                                 @RequestBody @Valid CommentRequest commentRequest) {
        return commentService.createComment(commentRequest, userToken);
    }

}
