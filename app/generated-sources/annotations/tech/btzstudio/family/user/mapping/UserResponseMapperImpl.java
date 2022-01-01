package tech.btzstudio.family.user.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tech.btzstudio.family.model.entity.Role;
import tech.btzstudio.family.model.entity.User;
import tech.btzstudio.family.user.dto.RoleResponse;
import tech.btzstudio.family.user.dto.UserResponse;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-31T13:42:43+0000",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class UserResponseMapperImpl implements UserResponseMapper {

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;
        List<RoleResponse> roles = null;

        firstName = user.getFirstName();
        lastName = user.getLastName();
        roles = roleListToRoleResponseList( user.getRoles() );

        String id = map(user.getId());

        UserResponse userResponse = new UserResponse( id, firstName, lastName, roles );

        return userResponse;
    }

    protected RoleResponse roleToRoleResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        String id = null;
        String name = null;

        id = map( role.getId() );
        name = role.getName();

        RoleResponse roleResponse = new RoleResponse( id, name );

        return roleResponse;
    }

    protected List<RoleResponse> roleListToRoleResponseList(List<Role> list) {
        if ( list == null ) {
            return null;
        }

        List<RoleResponse> list1 = new ArrayList<RoleResponse>( list.size() );
        for ( Role role : list ) {
            list1.add( roleToRoleResponse( role ) );
        }

        return list1;
    }
}
