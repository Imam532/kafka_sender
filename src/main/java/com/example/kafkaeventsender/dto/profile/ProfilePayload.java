package com.example.kafkaeventsender.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfilePayload {
    private String city;
    private String email;
    private String about;
    private String country;
    private String realName;
    private String avatarId;
    private String nickname;
    private String teamName;
    private String username;
    private String avatarSrc;
    private String authStatus;
    private String publicName;
    private String phoneNumber;
    private String backgroundId;
    private String backgroundSrc;
    private String stateProvince;
    private LocalDateTime memberSince;
    private int page;
    private int size;
    private String search;
}
