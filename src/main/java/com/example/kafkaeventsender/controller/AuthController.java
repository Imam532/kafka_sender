package com.example.kafkaeventsender.controller;


import com.example.kafkaeventsender.dto.auth.AuthResponse;
import com.example.kafkaeventsender.dto.auth.AuthUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/get-user")
@RequiredArgsConstructor
public class AuthController {
    private Random rand;

    {
        try {
            rand = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/{userId}")
    public AuthResponse checkAuth(@PathVariable String userId) {
        return new AuthResponse()
                .setPayload(new AuthUserInfo()
                        .setAuthStatus("registered")
                        .setEmail("test@test.mail")
                        .setDatetimeRegistration(LocalDateTime.now(Clock.systemUTC()))
                        .setUserid(userId)
                        .setUsername("testUser" + rand.nextInt())
                );
    }
}
