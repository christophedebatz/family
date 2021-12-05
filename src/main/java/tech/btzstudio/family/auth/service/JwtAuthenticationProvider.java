package tech.btzstudio.family.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tech.btzstudio.family.model.repository.UserRepository;

import java.util.UUID;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    /**
     * The JWT service.
     */
    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    /**
     * The user repository.
     */
    private final UserRepository userRepository;

    @Autowired
    public JwtAuthenticationProvider (JwtService jwtService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // nothing to do here
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String password = passwordEncoder.encode((String) authentication.getCredentials());
        return userRepository.findByEmailAndPassword(username, password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isForcePrincipalAsString () {
        return false;
    }
}
