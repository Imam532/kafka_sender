package com.example.kafkaeventsender.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProfileDto {
    private String userid;
    private String nickname;
    private String publicName;
    private String realName;
    private String country;
    private String about;
    private String avatarId;
    private String avatarSrc;
    private List<String> avatarThumbId;
    private List<String> avatarThumbSrc;
    private String backgroundId;
    private String backgroundSrc;
    private String teamName;
    private String stateProvince;
    private String city;
    private String phoneNumber;
    private String status;
    private String lastVisit;
    private String memberSince;
    private Boolean isFollowed;
    private Boolean isBlockByMe;
    private Boolean blockedMe;
    private Boolean friendRequestSent;
    private Boolean isFriend;
    private String username;
    private String email;
    private String authStatus;
}
