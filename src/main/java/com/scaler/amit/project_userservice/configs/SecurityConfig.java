package com.scaler.amit.project_userservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    //Create Security Filter Chain to bypass default security provided by Spring Security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(request -> {
                    try {
                        request.anyRequest()
                                .permitAll()
                                .and().cors().disable()
                                .csrf().disable();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .build();
    }
}
