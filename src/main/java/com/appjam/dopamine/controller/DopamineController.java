package com.appjam.dopamine.controller;

import com.appjam.dopamine.entity.User;
import com.appjam.dopamine.request.PickDopamineRequest;
import com.appjam.dopamine.service.AuthService;
import com.appjam.dopamine.service.DopamineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/dopamine")
@RequiredArgsConstructor
public class DopamineController {
    private final AuthService authService;
    private final DopamineService dopamineService;

    @GetMapping("/mydopamine")
    public ResponseEntity<Long> myDopamine() {
        User user = authService.getCurrentUser();
        if (user.getDopamine() == null) {
            return ResponseEntity.ok(0L);
        }
        return ResponseEntity.ok(user.getDopamine());
    }
    @PostMapping("/pick")
    public void pickDopamine(@RequestBody PickDopamineRequest request) {
        dopamineService.addDopamine(request);
    }

}
