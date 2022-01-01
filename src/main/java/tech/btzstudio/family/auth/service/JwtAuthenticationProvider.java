package tech.btzstudio.family.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Component;
import tech.btzstudio.family.model.repository.UserRepository;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    /**
     * The user repository.
     */
    private final UserRepository userRepository;

    @Autowired
    public JwtAuthenticationProvider (UserRepository userRepository) {
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
        String password = (new Argon2PasswordEncoder()).encode((String) authentication.getCredentials());
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
