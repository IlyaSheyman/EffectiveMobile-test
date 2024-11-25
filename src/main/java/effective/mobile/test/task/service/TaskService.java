package effective.mobile.test.task.service;

import effective.mobile.test.constants.Constants;
import effective.mobile.test.constants.Priority;
import effective.mobile.test.constants.Status;
import effective.mobile.test.constants.TaskFilter;
import effective.mobile.test.exceptions.model.AccessDeniedException;
import effective.mobile.test.exceptions.model.NotFoundException;
import effective.mobile.test.task.dto.task.*;
import effective.mobile.test.task.entity.Comment;
import effective.mobile.test.task.entity.Task;
import effective.mobile.test.task.mapper.TaskMapper;
import effective.mobile.test.task.storage.TaskRepository;
import effective.mobile.test.user.auth.service.JwtService;
import effective.mobile.test.user.entity.User;
import effective.mobile.test.user.storage.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;


    //ADMIN FUNCTIONALITY:
    public TaskCreatedDto createTask(TaskCreateRequest task, String userToken) {
        User author = getUserFromToken(userToken);

        if (!author.getRole().equals(Constants.Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("Только администратор может менять создавать задачи");
        }

        List<Comment> comments = new ArrayList<>();
        User assignee = getUserById(task.getAssigneeId());

        Task newTask = Task.builder()
                .assignee(assignee)
                .title(task.getTitle())
                .description(task.getDescription())
                .author(author)
                .priority(task.getPriority())
                .status(task.getStatus())
                .comments(comments)
                .build();

        Task saved = taskRepository.save(newTask);

        return taskMapper.toTaskCreatedDto(saved);
    }

    public TaskUpdatedDto updateTask(TaskUpdateRequest dto, String userToken) {
        User user = getUserFromToken(userToken);
        Task task = getTaskById(dto.getId());

        if (!user.getRole().equals(Constants.Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("Только администратор может менять обновлять задачи");
        }

        if (!user.getId().equals(task.getAuthor().getId())) {
            throw new AccessDeniedException("Только автор задачи может её обновить");
        }

        if (dto.getAssigneeId() != null) {
            User newAssignee = getUserById(dto.getAssigneeId());
            task.setAssignee(newAssignee);
        }
        if (dto.getPriority() != null) {
            task.setPriority(dto.getPriority());
        }
        if (dto.getDescription() != null) {
            task.setDescription(dto.getDescription());
        }
        if (dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }
        if (dto.getTitle() != null) {
            task.setTitle(dto.getTitle());
        }

        return taskMapper.toTaskUpdatedDto(taskRepository.save(task));
    }

    public void deleteTask(int id, String userToken) {
        User user = getUserFromToken(userToken);
        Task task = getTaskById(id);

        if (!user.getRole().equals(Constants.Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("Только администратор может менять удалять задачи");
        }

        if (!user.getId().equals(task.getAuthor().getId())) {
            throw new AccessDeniedException("Только автор задачи может её удалить");
        }

        taskRepository.delete(task);
    }

    public List<TaskGetDto> getCreatedTasks(String userToken,
                                            Pageable pageable,
                                            TaskFilter filter,
                                            Integer assigneeId) {
        User user = getUserFromToken(userToken);

        if (!user.getRole().equals(Constants.Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("Только администратор может получать список созданных задач");
        }

        Page<Task> tasksPage = switch (filter) {
            case PENDING -> taskRepository.findByAuthorIdAndStatus(user.getId(), Status.PENDING, pageable);
            case IN_PROGRESS -> taskRepository.findByAuthorIdAndStatus(user.getId(), Status.IN_PROGRESS, pageable);
            case COMPLETED -> taskRepository.findByAuthorIdAndStatus(user.getId(), Status.COMPLETED, pageable);
            case LOW_PRIORITY -> taskRepository.findByAuthorIdAndPriority(user.getId(), Priority.LOW, pageable);
            case MEDIUM_PRIORITY -> taskRepository.findByAuthorIdAndPriority(user.getId(), Priority.MEDIUM, pageable);
            case HIGH_PRIORITY -> taskRepository.findByAuthorIdAndPriority(user.getId(), Priority.HIGH, pageable);
            case ALL -> taskRepository.findByAuthorId(user.getId(), pageable);
        };

        return tasksPage.getContent().stream()
                .filter(task -> assigneeId == null ||
                        (task.getAssignee() != null && task.getAssignee().getId().equals(assigneeId)))
                .map(taskMapper::toTaskGetDto)
                .toList();
    }

    //USER FUNCTIONALITY:
    public List<TaskGetDto> getTasksByAssignee(String userToken, Pageable pageable, TaskFilter filter) {
        User user = getUserFromToken(userToken);
        Page<Task> tasksPage = switch (filter) {
            case PENDING -> taskRepository.findByAssigneeIdAndStatus(user.getId(), Status.PENDING, pageable);
            case IN_PROGRESS -> taskRepository.findByAssigneeIdAndStatus(user.getId(), Status.IN_PROGRESS, pageable);
            case COMPLETED -> taskRepository.findByAssigneeIdAndStatus(user.getId(), Status.COMPLETED, pageable);
            case LOW_PRIORITY -> taskRepository.findByAssigneeIdAndPriority(user.getId(), Priority.LOW, pageable);
            case MEDIUM_PRIORITY -> taskRepository.findByAssigneeIdAndPriority(user.getId(), Priority.MEDIUM, pageable);
            case HIGH_PRIORITY -> taskRepository.findByAssigneeIdAndPriority(user.getId(), Priority.HIGH, pageable);
            case ALL -> taskRepository.findByAssigneeId(user.getId(), pageable);
        };

        return tasksPage.getContent().stream()
                .map(taskMapper::toTaskGetDto)
                .toList();
    }

    public TaskUpdatedDto updateTaskStatus(UpdateStatusRequest statusRequest, String userToken) {
        User user = getUserFromToken(userToken);
        Task task = getTaskById(statusRequest.getTaskId());

        if (!task.getAssignee().getId().equals(user.getId())) {
            throw new AccessDeniedException("Только исполнитель и автор задачи могут менять статус");
        }

        task.setStatus(statusRequest.getStatus());
        return taskMapper.toTaskUpdatedDto(taskRepository.save(task));
    }

    private User getUserFromToken(String userToken) {
        int userId = jwtService.extractUserId(userToken);
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден"));
    }

    private Task getTaskById(int taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Задача с id " + taskId + " не найден"));
    }

    private User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден"));
    }

}