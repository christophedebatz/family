package tech.btzstudio.family.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tech.btzstudio.family.model.entity.User;

import java.util.function.BiFunction;

@Service
public class UserSessionSupplier implements BiFunction<String, String, UserSession> {

    /**
     * The authentication provider.
     */
    private final AuthenticationProvider authenticationProvider;

    /**
     * The JWT service.
     */
    private final JwtService jwtService;

    @Autowired
    public UserSessionSupplier (AuthenticationProvider authenticationProvider, JwtService jwtService) {
        this.authenticationProvider = authenticationProvider;
        this.jwtService = jwtService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserSession apply (String email, String password) {
        var credentials = new UsernamePasswordAuthenticationToken(email, password);

        try {
            Authentication auth = authenticationProvider.authenticate(credentials);

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(auth);

            User user = (User) auth.getPrincipal();
            return new UserSession(jwtService.generateToken(user), user);

        } catch (AuthenticationException ex) {
            throw new BadCredentialsException(email);
        }
    }
}
