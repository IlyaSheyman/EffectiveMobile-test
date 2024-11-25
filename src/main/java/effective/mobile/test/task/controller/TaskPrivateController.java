package effective.mobile.test.task.controller;

import effective.mobile.test.constants.TaskFilter;
import effective.mobile.test.task.dto.task.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/tasks")
@Tag(name = "Admin tasks controller", description = "Admin API for managing tasks")
public class TaskPrivateController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    @Operation(summary = "Create a task", description = "Allows an administrator to create a new task.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task successfully created",
                    content = @Content(schema = @Schema(implementation = TaskCreatedDto.class))),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public TaskCreatedDto createTask(@RequestHeader(name = "Authorization") String userToken,
                                     @RequestBody @Valid TaskCreateRequest task) {
        return taskService.createTask(task, userToken);
    }

    @PutMapping
    @Operation(summary = "Update a task", description = "Allows an administrator to update an existing task.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task successfully updated",
                    content = @Content(schema = @Schema(implementation = TaskUpdatedDto.class))),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public TaskUpdatedDto updateTask(@RequestHeader(name = "Authorization") String userToken,
                                     @RequestBody @Valid TaskUpdateRequest task) {
        return taskService.updateTask(task, userToken);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Delete a task", description = "Allows an administrator to delete a task by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Task successfully deleted"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public void deleteTask(@RequestHeader(name = "Authorization") String userToken,
                           @RequestParam(name = "task_id") int id) {
        taskService.deleteTask(id, userToken);
    }

    @GetMapping("/created")
    @Operation(summary = "Get created tasks", description = "Retrieves tasks created by the user, optionally filtered by status, priority, or assignee.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of tasks retrieved",
                    content = @Content(schema = @Schema(implementation = TaskGetDto.class))),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public List<TaskGetDto> getCreatedTasks(
            @RequestHeader(name = "Authorization") String userToken,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ALL") String filter,
            @RequestParam(name = "assignee_id", required = false) Integer assigneeId) {

        Pageable pageable = PageRequest.of(page, size);
        return taskService.getCreatedTasks(userToken, pageable, TaskFilter.fromString(filter), assigneeId);
    }
}