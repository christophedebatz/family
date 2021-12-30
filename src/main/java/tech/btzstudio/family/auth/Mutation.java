package tech.btzstudio.family.auth;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import tech.btzstudio.family.auth.dto.AuthResponse;
import tech.btzstudio.family.auth.service.UserSessionSupplier;

@Component
public class Mutation implements GraphQLMutationResolver {

    private final UserSessionSupplier sessionSupplier;

    @Autowired
    public Mutation (UserSessionSupplier sessionSupplier) {
        this.sessionSupplier = sessionSupplier;
    }

    @PreAuthorize("isAnonymous()")
    public AuthResponse signin(final String email, final String password) {
        try {
            var session = sessionSupplier.apply(email, password);
            return new AuthResponse(session.token(), session.user());

        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "auth.disabled.user");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "auth.invalid.credentials");
        }
    }
}
