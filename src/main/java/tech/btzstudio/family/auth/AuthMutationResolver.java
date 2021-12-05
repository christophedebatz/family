package tech.btzstudio.family.auth;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import tech.btzstudio.family.auth.dto.AuthRequest;
import tech.btzstudio.family.auth.dto.AuthResponse;
import tech.btzstudio.family.auth.service.UserSessionSupplier;

@Component
public class AuthMutationResolver implements GraphQLMutationResolver {

    private final UserSessionSupplier sessionSupplier;

    public AuthMutationResolver (UserSessionSupplier sessionSupplier) {
        this.sessionSupplier = sessionSupplier;
    }

    @PreAuthorize("isAnonymous()")
    public AuthResponse signin(final AuthRequest request) {
        try {
            var session = sessionSupplier.apply(request.getEmail(), request.getPassword());
            return new AuthResponse(session.token(), session.user());

        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "auth.disabled.user");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "auth.invalid.credentials");
        }
    }
}
