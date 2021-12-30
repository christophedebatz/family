package tech.btzstudio.family.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.btzstudio.family.model.entity.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

    private static final String ISSUER_NAME = "tech.btzstudio";

    @Value("auth.jwt.key")
    private String jwtKey;

    @Value("auth.token.cache.ttl.days")
    private String tokenDaysTtl;

    /**
     *
     * @param user
     * @return
     */
    public String generateToken(final User user) {
        Date expireAt = Date.from(
                LocalDateTime.now()
                        .plusDays(Long.parseLong(tokenDaysTtl))
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );

        byte[] token = JWT.create()
                .withIssuer(ISSUER_NAME)
                .withSubject(String.valueOf(user.getId()))
                .withExpiresAt(expireAt)
                .sign(Algorithm.HMAC512(jwtKey))
                .getBytes(StandardCharsets.UTF_8)
        ;

        return new String(token, StandardCharsets.UTF_8);
    }

    /**
     *
     * @param token
     * @return
     */
    public Optional<DecodedJWT> decodeToken(String token) {
        try {
            return Optional.of(verifier().verify(token));
        } catch (JWTVerificationException ex) {
            return Optional.empty();
        }
    }

    private JWTVerifier verifier() {
        return JWT
            .require(Algorithm.HMAC512(jwtKey))
            .withIssuer(ISSUER_NAME)
            .build();
    }
}
