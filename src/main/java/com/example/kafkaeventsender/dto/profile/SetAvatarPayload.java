package com.example.kafkaeventsender.dto.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public record SetAvatarPayload(String avatarSrc, String avatarId, List<String> avatarThumbSrc, List<String> avatarThumbId) {
}
