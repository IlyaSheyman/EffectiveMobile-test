import effective.mobile.test.constants.Constants;
import effective.mobile.test.constants.Priority;
import effective.mobile.test.constants.TaskFilter;
import effective.mobile.test.exceptions.model.AccessDeniedException;
import effective.mobile.test.task.dto.task.*;
import effective.mobile.test.task.entity.Task;
import effective.mobile.test.task.mapper.TaskMapper;
import effective.mobile.test.task.service.TaskService;
import effective.mobile.test.task.storage.TaskRepository;
import effective.mobile.test.user.auth.service.JwtService;
import effective.mobile.test.user.entity.User;
import effective.mobile.test.user.storage.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testCreateTask_AdminUser_Success() {
        String token = "Bearer adminToken";
        User admin = User.builder()
                .id(1)
                .username("admin")
                .role(Constants.Role.ROLE_ADMIN)
                .build();

        User assignee = User.builder()
                .id(2)
                .username("assignee")
                .build();

        TaskCreateRequest request = TaskCreateRequest.builder()
                .title("New Task")
                .description("Task Description")
                .priority(Priority.HIGH)
                .assigneeId(2)
                .build();

        Task savedTask = Task.builder().id(1).title("New Task").build();
        TaskCreatedDto createdDto = new TaskCreatedDto();

        when(jwtService.extractUserId("adminToken")).thenReturn(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(admin));
        when(userRepository.findById(2)).thenReturn(Optional.of(assignee));
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);
        when(taskMapper.toTaskCreatedDto(savedTask)).thenReturn(createdDto);

        TaskCreatedDto result = taskService.createTask(request, token);

        assertNotNull(result);
        verify(taskRepository).save(any(Task.class));
        verify(taskMapper).toTaskCreatedDto(savedTask);
    }

    @Test
    void testCreateTask_NonAdminUser_ThrowsAccessDeniedException() {
        String token = "Bearer userToken";
        User user = User.builder().id(1).username("user").role(Constants.Role.ROLE_USER).build();
        TaskCreateRequest request = TaskCreateRequest.builder()
                .title("New Task")
                .description("Task Description")
                .priority(Priority.HIGH)
                .assigneeId(2)
                .build();

        when(jwtService.extractUserId("userToken")).thenReturn(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        assertThrows(AccessDeniedException.class, () -> taskService.createTask(request, token));
    }

    @Test
    void testUpdateTask_Success() {
        String token = "Bearer adminToken";

        User admin = User.builder()
                .id(1)
                .username("admin")
                .role(Constants.Role.ROLE_ADMIN)
                .build();

        Task task = Task.builder()
                .id(1)
                .author(admin)
                .build();

        TaskUpdateRequest updateRequest = TaskUpdateRequest.builder()
                .id(1)
                .title("Updated Task Title")
                .build();

        Task updatedTask = Task.builder()
                .id(1)
                .title("Updated Task Title")
                .build();
        TaskUpdatedDto updatedDto = new TaskUpdatedDto();

        when(jwtService.extractUserId("adminToken")).thenReturn(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(admin));
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);
        when(taskMapper.toTaskUpdatedDto(updatedTask)).thenReturn(updatedDto);

        TaskUpdatedDto result = taskService.updateTask(updateRequest, token);

        assertNotNull(result);
        verify(taskRepository).save(task);
        verify(taskMapper).toTaskUpdatedDto(updatedTask);
    }

    @Test
    void testDeleteTask_Success() {
        String token = "Bearer adminToken";
        User admin = User.builder()
                .id(1)
                .username("admin")
                .role(Constants.Role.ROLE_ADMIN)
                .build();

        Task task = Task.builder()
                .id(1)
                .author(admin)
                .build();

        when(jwtService.extractUserId("adminToken")).thenReturn(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(admin));
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));

        taskService.deleteTask(1, token);

        verify(taskRepository).delete(task);
    }

    @Test
    void testGetCreatedTasks_AdminUser_Success() {
        String token = "Bearer adminToken";
        User admin = User.builder().id(1).username("admin").role(Constants.Role.ROLE_ADMIN).build();
        Task task = Task.builder().id(1).title("Task 1").build();
        Page<Task> tasksPage = new PageImpl<>(List.of(task));
        Pageable pageable = Pageable.ofSize(10);
        TaskGetDto taskGetDto = new TaskGetDto();

        when(jwtService.extractUserId("adminToken")).thenReturn(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(admin));
        when(taskRepository.findByAuthorId(1, pageable)).thenReturn(tasksPage);
        when(taskMapper.toTaskGetDto(task)).thenReturn(taskGetDto);

        List<TaskGetDto> result = taskService.getCreatedTasks(token, pageable, TaskFilter.ALL, null);

        assertEquals(1, result.size());
        verify(taskRepository).findByAuthorId(1, pageable);
    }
}
