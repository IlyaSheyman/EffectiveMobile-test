package effective.mobile.test.task.controller;

import effective.mobile.test.task.dto.TaskCreateRequest;
import effective.mobile.test.task.dto.TaskCreatedDto;
import effective.mobile.test.task.dto.TaskUpdateRequest;
import effective.mobile.test.task.dto.TaskUpdatedDto;
import effective.mobile.test.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
                                     @RequestBody TaskUpdateRequest task) {
        return taskService.updateTask(task, userToken);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteTask(@RequestHeader(name = "Authorization") String userToken,
                           @RequestParam(name = "task_id") int id) {
        taskService.deleteTask(id, userToken);
    }
}