package com.appjam.dopamine.global.config;

import com.appjam.dopamine.global.jwt.JwtFilter;
import com.appjam.dopamine.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtProvider jwtProvider;

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(
                new JwtFilter(jwtProvider),
                UsernamePasswordAuthenticationFilter.class
        );
    }
}