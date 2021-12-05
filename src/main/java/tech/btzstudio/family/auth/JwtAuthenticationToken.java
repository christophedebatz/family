package tech.btzstudio.family.auth;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import tech.btzstudio.family.model.entity.User;

@Getter
public class JwtAuthenticationToken extends PreAuthenticatedAuthenticationToken {

    public JwtAuthenticationToken(Object aPrincipal, Object aCredentials) {
        super(aPrincipal, aCredentials);
    }

    @Builder
    public JwtAuthenticationToken(User principal, WebAuthenticationDetails details) {
        super(principal, null, principal.getAuthorities());
        super.setDetails(details);
    }
}