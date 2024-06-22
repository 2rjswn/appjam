package com.appjam.dopamine.controller;

import com.appjam.dopamine.request.LoginRequest;
import com.appjam.dopamine.response.LoginResponse;
import com.appjam.dopamine.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<LoginRequest> signup(@RequestBody @Valid LoginRequest request) {
        authService.signupUser(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signin(@Valid @RequestBody LoginRequest request){
        LoginResponse response = authService.signinUser(request);
        return ResponseEntity.ok().body(response);
    }


}
