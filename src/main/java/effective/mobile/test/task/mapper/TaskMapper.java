package effective.mobile.test.task.mapper;

import effective.mobile.test.task.dto.task.TaskCreatedDto;
import effective.mobile.test.task.dto.task.TaskGetDto;
import effective.mobile.test.task.dto.task.TaskUpdatedDto;
import effective.mobile.test.task.entity.Task;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskCreatedDto toTaskCreatedDto(Task task);
    TaskUpdatedDto toTaskUpdatedDto(Task task);
    TaskGetDto toTaskGetDto(Task task);
}
