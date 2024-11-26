package effective.mobile.test.task.controller;

import effective.mobile.test.constants.TaskFilter;
import effective.mobile.test.task.dto.comment.CommentDto;
import effective.mobile.test.task.dto.comment.CommentRequest;
import effective.mobile.test.task.dto.task.TaskGetDto;
import effective.mobile.test.task.dto.task.TaskUpdatedDto;
import effective.mobile.test.task.dto.task.UpdateStatusRequest;
import effective.mobile.test.task.service.CommentService;
import effective.mobile.test.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User tasks controller", description = "User API for interacting with tasks and comments")
public class TaskPublicController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private CommentService commentService;

    @Operation(
            summary = "Get assigned tasks",
            description = "Retrieve a paginated list of tasks assigned to the current user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved tasks",
                    content = @Content(schema = @Schema(implementation = TaskGetDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input parameters"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied"
            )
    })
    @GetMapping("/my-tasks")
    public List<TaskGetDto> getMyTasks(
            @RequestHeader(name = "Authorization") String userToken,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ALL") String filter) {

        Pageable pageable = PageRequest.of(page, size);
        return taskService.getTasksByAssignee(userToken, pageable, TaskFilter.fromString(filter));
    }

    @Operation(
            summary = "Update task status",
            description = "Update the status of a task assigned to the user or created by them."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Task status successfully updated",
                    content = @Content(schema = @Schema(implementation = TaskUpdatedDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found"
            )
    })
    @PatchMapping("/status")
    @Transactional
    public TaskUpdatedDto updateTaskStatus(@RequestHeader(name = "Authorization") String userToken,
                                           @RequestBody @Valid UpdateStatusRequest statusRequest) {
        return taskService.updateTaskStatus(statusRequest, userToken);
    }

    @Operation(
            summary = "Add a comment to a task",
            description = "Add a new comment to a task. The user must have access to the task."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment successfully added",
                    content = @Content(schema = @Schema(implementation = CommentDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found"
            )
    })
    @PostMapping("/comments")
    @Transactional
    public CommentDto addComment(@RequestHeader(name = "Authorization") String userToken,
                                 @RequestBody @Valid CommentRequest commentRequest) {
        return commentService.createComment(commentRequest, userToken);
    }

}
