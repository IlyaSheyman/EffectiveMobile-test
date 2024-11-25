package effective.mobile.test.task.mapper;


import effective.mobile.test.task.dto.comment.CommentDto;
import effective.mobile.test.task.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "author.username", target = "authorUsername")
    CommentDto toCommentDto(Comment save);
}
