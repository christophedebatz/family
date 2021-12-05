package tech.btzstudio.family.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.btzstudio.family.user.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.function.Predicate.not;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    /**
     * Name of authorization header.
     */
    private static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * Bearer pattern.
     */
    private static final Pattern BEARER_PATTERN = Pattern.compile("^Bearer (.+?)$");

    /**
     * The user service.
     */
    private final UserService userService;

    @Autowired
    public JwtRequestFilter (UserService userService) {
        this.userService = userService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        getToken(request)
                .map(userService::resolveUserFromToken)
                .map(user -> JwtAuthenticationToken
                        .builder()
                        .principal(user.orElseThrow(() -> new BadCredentialsException("invalid.credentials")))
                        .details(new WebAuthenticationDetailsSource().buildDetails(request))
                        .build())
                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));

        filterChain.doFilter(request, response);
    }

    private Optional<String> getToken (HttpServletRequest request) {
        return Optional
                .ofNullable(request.getHeader(AUTHORIZATION_HEADER))
                .filter(not(String::isEmpty))
                .map(BEARER_PATTERN::matcher)
                .filter(Matcher::find)
                .map(matcher -> matcher.group(1));
    }
}