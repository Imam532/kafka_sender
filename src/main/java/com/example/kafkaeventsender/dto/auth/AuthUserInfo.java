package com.example.kafkaeventsender.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthUserInfo {
    private String email;
    private String userid;
    private String username;
    private String authStatus;
    private LocalDateTime datetimeRegistration;
}
