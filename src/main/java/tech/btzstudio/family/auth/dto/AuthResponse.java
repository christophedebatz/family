package tech.btzstudio.family.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tech.btzstudio.family.model.entity.User;

@Getter @Setter @AllArgsConstructor
public class AuthResponse {

    private String token;

    private User user;

}
