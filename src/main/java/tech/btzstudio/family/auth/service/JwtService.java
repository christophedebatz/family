package tech.btzstudio.family.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
public class JwtService {

    @Value("auth.jwt.key")
    private String jwtKey;

    @Value("auth.token.cache.ttl.days")
    private Long tokenDaysTtl;

    public String generateToken(final Authentication auth) {
        var expiredAt = Date.from(
                LocalDate.now().plusDays(
                        Optional.ofNullable(tokenDaysTtl).orElse(10L)
                ).atStartOfDay(ZoneId.systemDefault()).toInstant());

        var claims = new HashMap<String, Object>();
        claims.put("roles", auth.getAuthorities());
        claims.put("userId", auth.)

        return Jwts.builder()
                .setSubject(auth.getPrincipal().toString())
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setIssuer("tech.btzstudio")
                .setExpiration(expiredAt)
                .signWith(SignatureAlgorithm.HS256, jwtKey)
                .compact();
    }

    public Optional<DecodedJWT> decodeToken(String token) {
        try {
            return Optional.of(verifier().verify(token));
        } catch (JWTVerificationException ex) {
            return Optional.empty();
        }
    }

    private JWTVerifier verifier() {
        JWT
            .require(Algorithm.HMAC512(jwtKey))
            .withIssuer("tech.btzstudio")
            .build();
    }
}
