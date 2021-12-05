package tech.btzstudio.family.auth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
public class AuthRequest {

    @Email
    private String email;

    @NotBlank
    private String password;

}
