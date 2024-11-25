package effective.mobile.test.task.mapper;


import effective.mobile.test.task.dto.comment.CommentDto;
import effective.mobile.test.task.entity.Comment;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto toCommentDto(Comment save);
}
