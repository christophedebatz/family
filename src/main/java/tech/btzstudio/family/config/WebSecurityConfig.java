package tech.btzstudio.family.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import tech.btzstudio.family.auth.JwtRequestFilter;

@Configuration
@EnableWebFluxSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationProvider authenticationProvider;

    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public WebSecurityConfig (AuthenticationProvider authenticationProvider, JwtRequestFilter jwtRequestFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .addFilterBefore(jwtRequestFilter, RequestHeaderAuthenticationFilter.class); // Filter
    }
}