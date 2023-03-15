package com.example.kafkaeventsender.controller;

import com.example.kafkaeventsender.client.EventClient;
import com.example.kafkaeventsender.dto.profile.FindUserProfilePayload;
import com.example.kafkaeventsender.dto.profile.GetFriendsPayload;
import com.example.kafkaeventsender.dto.profile.ProfilePayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inuigaming.inuieventstarter.kafka.MessageService;
import inui.models.constants.Events;
import inui.models.kafka.KafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static inui.models.constants.Events.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private static final String PROFILE_TOPIC = "inui-profile-service";

    private final EventClient eventClient;
    private final MessageService messageService;

    @SneakyThrows
    @GetMapping("/name")
    private String getProfileByPublicName(@RequestParam("userId") String userId, @RequestParam("publicName") String publicName) {
        ProfilePayload profilePayload = new ProfilePayload().setPublicName(publicName);
        String s = new ObjectMapper().writeValueAsString(profilePayload);
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(Events.GET_USER_PROFILE, userId));
        messageService.sendMessage(kafkaMessage, "inui-profile-service");
        return "ok";
    }

    @SneakyThrows
    @GetMapping("/followed")
    private String getProfileFollowed(@RequestParam("userId") String userId, @RequestParam("publicName") String publicName) {

        String s = new ObjectMapper().writeValueAsString(new ProfilePayload()
                .setPublicName(publicName)
                .setPage(0)
                .setSize(20));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(GET_FOLLOWED, userId));
        messageService.sendMessage(kafkaMessage, "inui-profile-service");
        return "ok";
    }

    @SneakyThrows
    @GetMapping("/follower")
    private String getProfileFollower(@RequestParam("userId") String userId, @RequestParam("publicName") String publicName) {

        String s = new ObjectMapper().writeValueAsString(new ProfilePayload()
                .setPublicName(publicName)
                .setPage(0)
                .setSize(20));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(GET_FOLLOWERS, userId));
        messageService.sendMessage(kafkaMessage, "inui-profile-service");
        return "ok";
    }

    @SneakyThrows
    @GetMapping("/friends")
    private String getFriends(@RequestParam("userId") String userId, @RequestParam("publicName") String publicName) {

        String s = new ObjectMapper().writeValueAsString(new GetFriendsPayload()
                .setPublicName(publicName)
                .setSelfConfirm(true)
                .setFriendConfirm(true)
                .setPage(0)
                .setPageSize(20));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(GET_FRIENDS, userId));
        messageService.sendMessage(kafkaMessage, "inui-profile-service");
        return "ok";
    }

    @SneakyThrows
    @GetMapping("/findProfile")
    private String findProfile(@RequestParam("userId") String userId, @RequestParam("nickname") String nickname) {

        String s = new ObjectMapper().writeValueAsString(new FindUserProfilePayload()
                .setNickname(nickname)
                .setHideFriends(true)
                .setPage(0)
                .setSize(20));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(FIND_USER_PROFILE, userId));
        messageService.sendMessage(kafkaMessage, "inui-profile-service");
        return "ok";
    }

    private Map<String, String> getHeaders(String event, String userId) {
        return Map.of(
                "X-APPLICATION-NAME", "inui-api-gateway",
                "X-TIMESTAMP", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                "X-STATUS", "SUCCESS",
                "X-USER-ID", userId,
                "X-EVENT-NAME", event,
                "X-TRACE-ID", "a3043784-7334-4091-98ae-cbc2a2c131f5");
    }
}
