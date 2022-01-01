package tech.btzstudio.family.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.btzstudio.family.model.repository.UserRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {


        return Optional.ofNullable(this.userRepository.findByEmail(email))
            .orElseThrow(() -> new UsernameNotFoundException("user.not.found"));
    }
}
