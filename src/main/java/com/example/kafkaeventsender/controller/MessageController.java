package com.example.kafkaeventsender.controller;

import com.example.kafkaeventsender.client.EventClient;
import com.inuigaming.inuieventstarter.JsonConverter;
import com.inuigaming.inuieventstarter.convertors.KafkaMessageCreator;
import com.inuigaming.inuieventstarter.kafka.MessageService;
import inui.models.constants.Events;
import inui.models.kafka.KafkaMessage;
import inui.models.statistic.Match;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping("/send")
@RequiredArgsConstructor
public class MessageController {
//    private final KafkaEventSender eventSender;
    private final EventClient eventClient;
    private final MessageService messageService;
    private final KafkaMessageCreator kafkaMessageCreator;

    @SneakyThrows
    @GetMapping
    public String matchStart() {
        String s = IOUtils.toString(requireNonNull(getSystemResourceAsStream("match_start.json")), UTF_8);
        Match match = JsonConverter.objectFromJson(s, Match.class);
        KafkaMessage message = kafkaMessageCreator.getMessage(match,
                Map.of(
                        "X-APPLICATION-NAME", "inui-api-gateway",
                        "X-TIMESTAMP", "2023-01-29T17:04:14.419316967Z",
                        "X-STATUS", "SUCCESS",
                        "X-USER-ID", "RVKVIM5VJSTFX68O",
                        "X-EVENT-NAME", Events.NEW_MATCH_START,
                        "X-TRACE-ID", "a3043784-7334-4091-98ae-cbc2a2c131f5"));
        messageService.sendMessage(message, "inui-gamestats-service");
        return "ok";
    }

    @GetMapping("/event")
    private String send2() {
        eventClient.sendMessages();
        return "ok";
    }

    @GetMapping("/v1/{event}")
    private String send3(@PathVariable String event, @RequestParam("userId") String userId) {
        String getUserStat = Events.GET_USER_STAT;
//        eventSender.sendMessageToEvent(event, userId);
        return "ok";
    }

    @GetMapping("/match/{event}")
    private String send4(@PathVariable String event, @RequestParam("matchId") String matchId) {
//        eventSender.sendMessageToEvent(event, matchId);
        return "ok";
    }
}
