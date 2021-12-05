package tech.btzstudio.family.user.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.btzstudio.family.auth.service.JwtService;
import tech.btzstudio.family.model.entity.User;
import tech.btzstudio.family.model.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private JwtService jwtService;

    private UserRepository userRepository;

    @Transactional
    public Optional<User> resolveUserFromToken(final String token) {
        return jwtService.decodeToken(token)
                .map(DecodedJWT::getSubject)
                .map(userRepository::findByEmail);
    }
}
}
