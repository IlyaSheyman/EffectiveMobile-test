package effective.mobile.test.task.service;

import effective.mobile.test.exceptions.model.NotFoundException;
import effective.mobile.test.task.dto.TaskCreateRequest;
import effective.mobile.test.task.dto.TaskCreatedDto;
import effective.mobile.test.task.dto.TaskUpdateRequest;
import effective.mobile.test.task.dto.TaskUpdatedDto;
import effective.mobile.test.user.auth.service.JwtService;
import effective.mobile.test.user.entity.User;
import effective.mobile.test.user.storage.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final JwtService jwtService;
    private UserRepository userRepository;

    public TaskCreatedDto createTask(@Valid TaskCreateRequest task, String userToken) {
        User author = getUserFromToken(userToken);
    }

    public TaskUpdatedDto updateTask(TaskUpdateRequest task, String userToken) {
        User user = getUserFromToken(userToken);
    }

    public void deleteTask(int id, String userToken) {
        User user = getUserFromToken(userToken);
    }

    private User getUserFromToken(String userToken) {
        int userId = jwtService.extractUserId(userToken);
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден"));
    }
}
