package com.example.springbootCRUDOperationsLearning.springbootCRUDOperationsLearning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.net.http.HttpRequest;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf ( Customizer.withDefaults ()).authorizeHttpRequests (authorize->authorize.anyRequest ().authenticated ()).httpBasic (Customizer.withDefaults ()).formLogin (Customizer.withDefaults ());
    return http.build ();
}
}
