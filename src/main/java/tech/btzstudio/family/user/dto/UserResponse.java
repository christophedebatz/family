package tech.btzstudio.family.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor
public class UserResponse {

    private String id;

    private String firstName;

    private String lastName;

    private List<RoleResponse> roles;

}
