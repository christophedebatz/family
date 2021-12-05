package tech.btzstudio.family.auth;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import tech.btzstudio.family.auth.dto.AuthRequest;
import tech.btzstudio.family.auth.dto.AuthResponse;
import tech.btzstudio.family.auth.service.AuthService;
import tech.btzstudio.family.model.repository.UserRepository;

@Component
public class AuthMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("isAnonymous()")
    public AuthResponse signin(final AuthRequest request) {
        try {
            //String token = authService.signin(request.getEmail(), request.getPassword());
            return new AuthResponse(null);

        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "auth.disabled.user");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "auth.invalid.credentials");
        }
    }
}
