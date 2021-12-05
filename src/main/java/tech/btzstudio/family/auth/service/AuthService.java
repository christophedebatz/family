package tech.btzstudio.family.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tech.btzstudio.family.model.entity.User;
import tech.btzstudio.family.model.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public User signin(final String email, final String password) {
        var credentials = new UsernamePasswordAuthenticationToken(email, password);

        try {
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authenticationProvider.authenticate(credentials));

            return userRepository.findByEmailAndPassword(email, password);
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException(email);
        }

        return null;
    }
}
