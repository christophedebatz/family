package tech.btzstudio.family.user.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tech.btzstudio.family.model.entity.User;
import tech.btzstudio.family.user.dto.UserResponse;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class})
public interface UserResponseMapper {

    @Mapping(target = "id", expression = "java(map(user.getId()))")
    UserResponse toResponse(final User user);

    default String map(UUID uuid) {
        return uuid.toString();
    }
}
