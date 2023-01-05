package com.example.finalproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http
                .authorizeRequests()
                .antMatchers("/actuator/health").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/loggers").fullyAuthenticated()
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/metrics").fullyAuthenticated()
                .and()
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/api/**").fullyAuthenticated()
                .and()
                .oauth2ResourceServer().jwt();

        //H2 console now can run with this configuration
        http.csrf().disable();
        http.headers().frameOptions().disable();

        return http.build();
    }
}

