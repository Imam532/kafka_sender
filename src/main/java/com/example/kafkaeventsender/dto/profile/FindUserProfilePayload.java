package com.example.kafkaeventsender.dto.profile;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FindUserProfilePayload {
    private int page;
    private int size;
    private String nickname;
    private boolean hideFriends;
}
