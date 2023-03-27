package com.example.kafkaeventsender.controller;

import com.example.kafkaeventsender.client.EventClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inuigaming.inuieventstarter.kafka.MessageService;
import inui.models.blog.rq.RequestById;
import inui.models.constants.Events;
import inui.models.kafka.KafkaMessage;
import inui.models.statistic.rq.StatPayload;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping("/send")
@RequiredArgsConstructor
public class StatController {
//    private final KafkaEventSender eventSender;
    private final EventClient eventClient;
    private final MessageService messageService;


    @SneakyThrows
    @GetMapping
    public String matchStart() {
        String s = IOUtils.toString(requireNonNull(getSystemResourceAsStream("match_start.json")), UTF_8);
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(Events.NEW_MATCH_START, "userId"));
        messageService.sendMessage(kafkaMessage, "inui-gamestats-service");
        return "ok";
    }

    @GetMapping("/event")
    private String sendGameEvents(@RequestParam("file") String file) {
        eventClient.sendMessages(file);
        return "ok";
    }

    @SneakyThrows
    @GetMapping("/user/{publicName}")
    private String getUserStat(@PathVariable String publicName, @RequestParam("query") String userId) {
        StatPayload body = new StatPayload().setPublicName(publicName).setMatchId(userId);
        String s = new ObjectMapper().writeValueAsString(body);
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(Events.GET_USER_STAT, userId));
        messageService.sendMessage(kafkaMessage, "inui-gamestats-service");
        return "ok";
    }

    @SneakyThrows
    @GetMapping("/top3/{userId}")
    private String getUserStat(@PathVariable String userId) {
        StatPayload body = new StatPayload().setMatchId(userId);
        String s = new ObjectMapper().writeValueAsString(body);
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(Events.GET_TOP_3_PLAYER_STAT, userId));
        messageService.sendMessage(kafkaMessage, "inui-gamestats-service");
        return "ok";
    }

    @SneakyThrows
    @GetMapping("/last5/{userId}")
    private String getLast5Match(@PathVariable String userId) {
        StatPayload body = new StatPayload().setMatchId(userId);
        String s = new ObjectMapper().writeValueAsString(body);
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(Events.GET_LAST_5_MATCH_STAT, userId));
        messageService.sendMessage(kafkaMessage, "inui-gamestats-service");
        return "ok";
    }

    @GetMapping("/match/{matchId}")
    private String getMatchStat(@PathVariable String matchId, @RequestParam("query") String query) throws JsonProcessingException {
        StatPayload statPayload = new StatPayload().setMatchId(matchId);
        String s = new ObjectMapper().writeValueAsString(statPayload);

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(Events.GET_MATCH_STAT, query));
        messageService.sendMessage(kafkaMessage, "inui-gamestats-service");
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
