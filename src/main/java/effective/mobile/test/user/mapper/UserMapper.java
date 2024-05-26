package effective.mobile.test.user.mapper;

import effective.mobile.test.user.dto.user.UserSearchDto;
import effective.mobile.test.user.dto.user.UserUpdateDto;
import effective.mobile.test.user.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserUpdateDto toUserUpdateDto(User user);
    UserSearchDto toUserSearchDto(User user);
}