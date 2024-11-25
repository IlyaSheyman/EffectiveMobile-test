package effective.mobile.test.task.controller;

import effective.mobile.test.constants.TaskFilter;
import effective.mobile.test.task.dto.task.*;
import effective.mobile.test.task.service.TaskService;
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
public class TaskPrivateController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public TaskCreatedDto createTask(@RequestHeader(name = "Authorization") String userToken,
                                     @RequestBody @Valid TaskCreateRequest task) {
        return taskService.createTask(task, userToken);
    }

    @PutMapping
    public TaskUpdatedDto updateTask(@RequestHeader(name = "Authorization") String userToken,
                                     @RequestBody @Valid TaskUpdateRequest task) {
        return taskService.updateTask(task, userToken);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteTask(@RequestHeader(name = "Authorization") String userToken,
                           @RequestParam(name = "task_id") int id) {
        taskService.deleteTask(id, userToken);
    }

    @GetMapping("/created-tasks")
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