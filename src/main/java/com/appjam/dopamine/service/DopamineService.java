package com.appjam.dopamine.service;

import com.appjam.dopamine.entity.User;
import com.appjam.dopamine.request.PickDopamineRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DopamineService {
    private final AuthService authService;
    public void addDopamine(PickDopamineRequest request) {
        User user = authService.getCurrentUser();
        Long dopamineToAdd = Long.valueOf(Math.toIntExact(Long.parseLong(request.getDopamine())));
        user.setDopamine(user.getDopamine() + dopamineToAdd);
    }
}
