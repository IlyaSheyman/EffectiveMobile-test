package effective.mobile.test.task.service;

import effective.mobile.test.exceptions.model.NotFoundException;
import effective.mobile.test.task.dto.comment.CommentDto;
import effective.mobile.test.task.dto.comment.CommentRequest;
import effective.mobile.test.task.entity.Comment;
import effective.mobile.test.task.entity.Task;
import effective.mobile.test.task.mapper.CommentMapper;
import effective.mobile.test.task.storage.CommentRepository;
import effective.mobile.test.task.storage.TaskRepository;
import effective.mobile.test.user.auth.service.JwtService;
import effective.mobile.test.user.entity.User;
import effective.mobile.test.user.storage.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentDto createComment(CommentRequest commentRequest, String userToken) {
        User user = getUserFromToken(userToken);
        int taskId = commentRequest.getTaskId();
        Task task = getTaskById(taskId);

        Comment comment = Comment.builder()
                .text(commentRequest.getText())
                .author(user)
                .task(task)
                .build();

        return commentMapper.toCommentDto(commentRepository.save(comment));
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
