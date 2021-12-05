package tech.btzstudio.family.user.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.btzstudio.family.auth.service.JwtService;
import tech.btzstudio.family.model.entity.User;
import tech.btzstudio.family.model.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    /**
     * The Jwt service.
     */
    private final JwtService jwtService;

    /**
     * The user repository.
     */
    private final UserRepository userRepository;

    @Autowired
    public UserService (JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    /**
     * Returns the token associated {@link User}.
     *
     * @param token the user token.
     * @return the {@link User} or empty.
     */
    @Transactional
    public Optional<User> resolveUserFromToken(final String token) {
        return jwtService.decodeToken(token)
                .map(DecodedJWT::getSubject)
                .map(UUID::fromString)
                .flatMap(userRepository::findById);
    }
}
