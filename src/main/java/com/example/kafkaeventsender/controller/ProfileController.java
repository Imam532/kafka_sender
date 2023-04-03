package com.example.kafkaeventsender.controller;

import com.example.kafkaeventsender.dto.profile.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inuigaming.inuieventstarter.kafka.MessageService;
import inui.models.kafka.KafkaMessage;
import inui.models.profile.PatchUserProfileRq;
import inui.models.profile.ProfilePayload;
import inui.models.profile.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static inui.models.constants.Events.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private static final String PROFILE_TOPIC = "inui-profile-service";

    private final MessageService messageService;

    @SneakyThrows
    @GetMapping("/register/{userId}")
    public String userRegister(@PathVariable("userId") String userId) {
        String s = new ObjectMapper().writeValueAsString(new ProfilePayload());

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(USER_REGISTERED, userId));
        messageService.sendMessage(kafkaMessage, PROFILE_TOPIC);
        return HttpStatus.OK.getReasonPhrase();
    }

    @SneakyThrows
    @GetMapping("/status/{userId}")
    public String setUserStatus(@PathVariable("userId") String userId, @RequestParam String status) {
        String s = new ObjectMapper().writeValueAsString(new ProfilePayload()
                .setPage(0)
                .setSize(0)
                .setStatus(UserStatus.forValue(status))
        );

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(SET_USER_STATUS, userId));
        messageService.sendMessage(kafkaMessage, PROFILE_TOPIC);
        return HttpStatus.OK.getReasonPhrase();
    }

    @SneakyThrows
    @GetMapping("/status/audit/{userId}")
    public String setAuditUserStatus(@PathVariable("userId") String userId, @RequestParam String status) {
        String s = new ObjectMapper().writeValueAsString(new ProfilePayload()
                .setStatus(UserStatus.forValue(status))
        );

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(SET_USER_STATUS_AUDIT, userId));
        messageService.sendMessage(kafkaMessage, PROFILE_TOPIC);
        return HttpStatus.OK.getReasonPhrase();
    }

    @SneakyThrows
    @GetMapping("/post/{userId}")
    public String setPostCount(@PathVariable("userId") String userId, @RequestParam int count) {
        String s = new ObjectMapper().writeValueAsString(new SetPostsCountPayload(count));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(SET_POSTS_COUNT, userId));
        messageService.sendMessage(kafkaMessage, PROFILE_TOPIC);
        return HttpStatus.OK.getReasonPhrase();
    }

    @SneakyThrows
    @GetMapping("/follow/{userId}")
    public String follow(@PathVariable("userId") String userId, @RequestParam String publicName) {
        String s = new ObjectMapper().writeValueAsString(new ProfilePayload()
                .setPublicName(publicName));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(FOLLOW_USER, userId));
        messageService.sendMessage(kafkaMessage, PROFILE_TOPIC);
        return HttpStatus.OK.getReasonPhrase();
    }

    @SneakyThrows
    @GetMapping("/unfollow/{userId}")
    public String unfollow(@PathVariable("userId") String userId, @RequestParam String publicName) {
        String s = new ObjectMapper().writeValueAsString(new ProfilePayload()
                .setPublicName(publicName));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(UNFOLLOW_USER, userId));
        messageService.sendMessage(kafkaMessage, PROFILE_TOPIC);
        return HttpStatus.OK.getReasonPhrase();
    }

    @SneakyThrows
    @GetMapping("/background/{userId}")
    public String setBackground(@PathVariable("userId") String userId) {
        String s = new ObjectMapper().writeValueAsString(
                new SetBackgroundPayload("backgroundId", "backgroundSrc"));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(SET_BACKGROUND, userId));
        messageService.sendMessage(kafkaMessage, PROFILE_TOPIC);
        return HttpStatus.OK.getReasonPhrase();
    }

    @SneakyThrows
    @GetMapping("/avatar/{userId}")
    public String setAvatar(@PathVariable("userId") String userId) {
        String s = new ObjectMapper().writeValueAsString(
                new SetAvatarPayload("avatarSrc",
                        "avatarId",
                        List.of("avatarTrmbId"),
                        List.of("avatarTrmbIdSRc")));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(SET_AVATAR, userId));
        messageService.sendMessage(kafkaMessage, PROFILE_TOPIC);
        return HttpStatus.OK.getReasonPhrase();
    }

    @SneakyThrows
    @GetMapping("/auth-status/{userId}")
    public String setAuthStatus(@PathVariable("userId") String userId, @RequestParam String status) {
        String s = new ObjectMapper().writeValueAsString(new SetAuthStatusPayload(status));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(SET_USER_AUTH_STATUS, userId));
        messageService.sendMessage(kafkaMessage, PROFILE_TOPIC);
        return HttpStatus.OK.getReasonPhrase();
    }

    @SneakyThrows
    @PostMapping("/patch/{userId}")
    public String patchUserProfile(@PathVariable("userId") String userId, @RequestBody PatchUserProfileRq payload) {
        String s = new ObjectMapper().writeValueAsString(payload);

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(PATCH_USER_PROFILE, userId));
        messageService.sendMessage(kafkaMessage, PROFILE_TOPIC);
        return HttpStatus.OK.getReasonPhrase();
    }

    @SneakyThrows
    @GetMapping("/get/{userId}")
    public String getProfile(@PathVariable("userId") String userId, @RequestParam(required = false) String publicName) {
        String s = new ObjectMapper().writeValueAsString(new ProfilePayload().setPublicName(publicName));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(GET_USER_PROFILE, userId));
        messageService.sendMessage(kafkaMessage, PROFILE_TOPIC);
        return HttpStatus.OK.getReasonPhrase();
    }

    @SneakyThrows
    @GetMapping("/friend/{userId}")
    public String addToFriend(@PathVariable("userId") String userId, @RequestParam(required = false) String publicName) {
        String s = new ObjectMapper().writeValueAsString(new ProfilePayload().setPublicName(publicName));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(ADD_TO_FRIENDS, userId));
        messageService.sendMessage(kafkaMessage, PROFILE_TOPIC);
        return HttpStatus.OK.getReasonPhrase();
    }

    @SneakyThrows
    @GetMapping("/friends/{userId}")
    public String getFriends(@PathVariable String userId,
                             @RequestParam String publicName,
                             @RequestParam(required = false, defaultValue = "") String search,
                             @RequestParam(required = false, defaultValue = "true") Boolean isConfirmed
    ) {

        String s = new ObjectMapper().writeValueAsString(new ProfilePayload()
                .setPublicName(publicName)
                .setSelfConfirm(isConfirmed)
                .setFriendConfirm(true)
                .setSearch(search)
                .setPage(0)
                .setSize(20));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(GET_FRIENDS, userId));
        messageService.sendMessage(kafkaMessage, PROFILE_TOPIC);
        return HttpStatus.OK.getReasonPhrase();
    }


    @SneakyThrows
    @GetMapping("/followed/{userId}")
    public String getFollowed(@PathVariable String userId,
                              @RequestParam String publicName,
                              @RequestParam(required = false, defaultValue = "") String search
    ) {

        String s = new ObjectMapper().writeValueAsString(new ProfilePayload()
                .setPublicName(publicName)
                .setSearch(search)
                .setPage(0)
                .setSize(20));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(GET_FOLLOWED, userId));
        messageService.sendMessage(kafkaMessage, PROFILE_TOPIC);
        return HttpStatus.OK.getReasonPhrase();
    }

    @SneakyThrows
    @GetMapping("/follower/{userId}")
    public String getFollower(@PathVariable String userId,
                              @RequestParam String publicName,
                              @RequestParam(required = false, defaultValue = "") String search
    ) {

        String s = new ObjectMapper().writeValueAsString(new ProfilePayload()
                .setPublicName(publicName)
                .setSearch(search)
                .setPage(0)
                .setSize(20));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(GET_FOLLOWERS, userId));
        messageService.sendMessage(kafkaMessage, PROFILE_TOPIC);
        return HttpStatus.OK.getReasonPhrase();
    }

    @SneakyThrows
    @GetMapping("/block/{userId}")
    public String setBlock(@PathVariable String userId,
                           @RequestParam String publicName,
                           @RequestParam(required = false, defaultValue = "") String search
    ) {

        String s = new ObjectMapper().writeValueAsString(new ProfilePayload()
                .setPublicName(publicName)
                .setSearch(search)
                .setPage(0)
                .setSize(20));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(BLOCK_USER, userId));
        messageService.sendMessage(kafkaMessage, PROFILE_TOPIC);
        return HttpStatus.OK.getReasonPhrase();
    }

    @SneakyThrows
    @GetMapping("/unblock/{userId}")
    public String setUnblock(@PathVariable String userId,
                             @RequestParam String publicName,
                             @RequestParam(required = false, defaultValue = "") String search
    ) {

        String s = new ObjectMapper().writeValueAsString(new ProfilePayload()
                .setPublicName(publicName)
                .setSearch(search)
                .setPage(0)
                .setSize(20));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(UNBLOCK_USER, userId));
        messageService.sendMessage(kafkaMessage, PROFILE_TOPIC);
        return HttpStatus.OK.getReasonPhrase();
    }

    @SneakyThrows
    @GetMapping("/block-list/{userId}")
    public String getBlockList(@PathVariable String userId,
                               @RequestParam String publicName,
                               @RequestParam(required = false, defaultValue = "") String search
    ) {

        String s = new ObjectMapper().writeValueAsString(new ProfilePayload()
                .setPublicName(publicName)
                .setSearch(search)
                .setPage(0)
                .setSize(20));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(GET_BLOCK_LIST, userId));
        messageService.sendMessage(kafkaMessage, PROFILE_TOPIC);
        return HttpStatus.OK.getReasonPhrase();
    }

    @SneakyThrows
    @GetMapping("/findProfiles/{userId}")
    public String findProfiles(@PathVariable String userId,
                               @RequestParam(required = false, defaultValue = "") String search) {

        String s = new ObjectMapper().writeValueAsString(new ProfilePayload()
                .setSearch(search)
                .setHideFriends(true)
                .setPage(0)
                .setSize(20));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(FIND_USER_PROFILE, userId));
        messageService.sendMessage(kafkaMessage, PROFILE_TOPIC);
        return HttpStatus.OK.getReasonPhrase();
    }

    @SneakyThrows
    @GetMapping("/setStatus")
    private String setStatus(@RequestParam("userId") String userId, @RequestParam("status") String status) {

        String s = new ObjectMapper().writeValueAsString(new SetUserStatusPayload(UserStatus.forValue(status)));

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(SET_USER_STATUS, userId));
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
