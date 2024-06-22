package com.appjam.dopamine.service;

import com.appjam.dopamine.entity.User;
import com.appjam.dopamine.entity.enums.Role;
import com.appjam.dopamine.global.jwt.JwtProvider;
import com.appjam.dopamine.repository.UserRepository;
import com.appjam.dopamine.request.LoginRequest;
import com.appjam.dopamine.response.LoginResponse;
import com.mysql.cj.exceptions.PasswordExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    @Transactional
    public void signupUser(LoginRequest request) {
        if(!userRepository.existsUserByName(request.getName())) {
            User user = User.builder()
                    .name(request.getName())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(String.valueOf(Role.USER))
                    .build();
            userRepository.save(user);
    } else throw new RuntimeException("중복 사용자 이름");
    }

    @Transactional
    public LoginResponse signinUser(LoginRequest request) {
        User user = userRepository.findByName(request.getName()).orElseThrow(() -> new RuntimeException("노 유저"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new PasswordExpiredException();

        String id = user.getId().toString();
        String password = user.getPassword();

        Role authorities = Role.valueOf(user.getRole());

        Authentication authentication = new UsernamePasswordAuthenticationToken(id, password, Collections.singleton(authorities));
        String access = jwtProvider.generateAccessToken(authentication);
        return new LoginResponse(access);
    }



    public User getCurrentUser() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        log.info(userId);
        return userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new RuntimeException("유저를 찾지 못했습니다"));
    }
}