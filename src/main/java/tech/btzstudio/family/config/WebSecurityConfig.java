package tech.btzstudio.family.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import tech.btzstudio.family.auth.JwtRequestFilter;
import tech.btzstudio.family.auth.service.JwtAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationProvider authenticationProvider;

    private final JwtRequestFilter jwtRequestFilter;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public WebSecurityConfig (AuthenticationProvider authenticationProvider, JwtRequestFilter jwtRequestFilter, JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        this.jwtRequestFilter = jwtRequestFilter;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return jwtAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .anyRequest().permitAll()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .addFilterBefore(jwtRequestFilter, RequestHeaderAuthenticationFilter.class); // Filter
    }
}