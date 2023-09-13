package com.example.security.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.
                csrf (csrf-> csrf.disable ()).
                authorizeHttpRequests (auth -> auth.requestMatchers ("/api/v1/auth/**").
                        permitAll ().anyRequest ().authenticated ()).
                sessionManagement (sess-> sess.sessionCreationPolicy ( SessionCreationPolicy.STATELESS )).
                authenticationProvider ( authenticationProvider).
                addFilterBefore ( jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class );

        return httpSecurity.build ();
    }
}
