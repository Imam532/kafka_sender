package com.example.kafkaeventsender.controller;

import com.inuigaming.inuieventstarter.kafka.MessageService;
import inui.models.kafka.KafkaMessage;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static inui.models.constants.Events.*;
import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {
    private final MessageService messageService;

    @SneakyThrows
    @GetMapping("/upload")
    public String setAuthStatus() {
        String s = IOUtils.toString(
                requireNonNull(getSystemResourceAsStream("upload_post.json")),
                UTF_8);

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(UPLOAD_POST, "VAXQZ0V6FLTX8PMJ"));
        messageService.sendMessage(kafkaMessage, "inui-blog-service");
        return HttpStatus.OK.getReasonPhrase();
    }
    @SneakyThrows
    @GetMapping("/post-filter")
    public String getFilteredPost() {
        String s = IOUtils.toString(
                requireNonNull(getSystemResourceAsStream("filtered_post.json")),
                UTF_8);

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(GET_WITH_FILTER, "VAXQZ0V6FLTX8PMJ"));
        messageService.sendMessage(kafkaMessage, "inui-blog-service");
        return HttpStatus.OK.getReasonPhrase();
    }

    @SneakyThrows
    @GetMapping("/get-feed")
    public String getFeed() {
        String s = IOUtils.toString(
                requireNonNull(getSystemResourceAsStream("get_feed.json")),
                UTF_8);

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setMessageBody(s);
        kafkaMessage.setHeaders(getHeaders(GET_FEED, "LER47QJSFN2ZJQ0V"));
        messageService.sendMessage(kafkaMessage, "inui-blog-service");
        return HttpStatus.OK.getReasonPhrase();
    }


    private Map<String, String> getHeaders(String event, String userId) {
        return Map.of(
                "X-APPLICATION-NAME", "inui-api-gateway",
                "X-TIMESTAMP", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                "X-USER-ID", userId,
                "X-EVENT-NAME", event,
                "X-TRACE-ID", "a3043784-7334-4091-98ae-cbc2a2c131f5");
    }
}
