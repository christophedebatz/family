package tech.btzstudio.family.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import tech.btzstudio.family.model.entity.User;
import tech.btzstudio.family.model.repository.UserRepository;

public class CredentialsAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = String.valueOf(authentication.getPrincipal());
        String password = String.valueOf(authentication.getCredentials());

        User user = userRepository.findByEmailAndPassword(email, password);

        return null;
    }
}
