//package com.example.kafkaeventsender.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import inui.models.constants.Events;
//import inui.models.kafka.KafkaMessage;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class KafkaEventSender {
//
//    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
//
//    public void sendMessageToEvent(String events, String userId) {
//        try {
//            kafkaTemplate.send("inui-gamestats-service", events, this.getKafkaMessage(events, userId));
//        } catch (JsonProcessingException e) {
//            log.error("Error", e);
//        }
//
//    }
//
//    public void sendEventMessage(String topicName) {
//        try {
//            kafkaTemplate.send(topicName, Events.GET_USER_PROFILE, this.getKafkaMessage2());
//        } catch (JsonProcessingException e) {
//            log.error("Error", e);
//        }
//    }
//
//    private KafkaMessage getKafkaMessage(String event, String userId) throws JsonProcessingException {
//        KafkaMessage kafkaMessage = new KafkaMessage();
//        kafkaMessage.setHeaders(Map.of(
//                "X-APPLICATION-NAME", "inui-api-gateway",
//                "X-TIMESTAMP", "2023-01-29T17:04:14.419316967Z",
//                "X-STATUS", "SUCCESS",
//                "X-USER-ID", (userId == null) ? "RVKVIM5VJSTFX68O" : userId,
//                "X-EVENT-NAME", event,
//                "X-TRACE-ID", "a3043784-7334-4091-98ae-cbc2a2c131f5"));
//
//        kafkaMessage.setMessageBody("{" +
//                "\"id\": \""+ userId +"\"" +
//                "}");
//        return kafkaMessage;
//    }
//
//    private KafkaMessage getKafkaMessage2() throws JsonProcessingException {
//        KafkaMessage kafkaMessage = new KafkaMessage();
//        kafkaMessage.setHeaders(Map.of(
//                "X-APPLICATION-NAME", "inui-api-gateway",
//                "X-TIMESTAMP", "2023-01-29T17:04:14.419316967Z",
//                "X-STATUS", "SUCCESS",
//                "X-USER-ID", "RVKVIM5VJSTFX68O",
//                "X-EVENT-NAME", Events.NEW_MATCH_START,
//                "X-TRACE-ID", "a3043784-7334-4091-98ae-cbc2a2c131f5"));
//        kafkaMessage.setMessageBody(this.getMessage());
//
//        kafkaMessage.setMessageBody("""
//                {
//                  "matchId": "123",
//                  "startTime": "2023-02-08T19:25:07.2135677",
//                  "type": "game2x2",
//                  "left": [
//                    {
//                      "userId": "userId1",
//                      "nickname": "Kuzmich",
//                      "avatarSrc": "",
//                      "owner": false,
//                      "steamId": "76561198073239050",
//                      "ban": null
//                    },
//                     {
//                      "userId": "userId2",
//                      "nickname": "Pasha",
//                      "avatarSrc": "",
//                      "owner": false,
//                      "steamId": "76561198355147054",
//                      "ban": null
//                    }
//                  ],
//                  "right": [
//                    {
//                      "userId": "userId3",
//                      "nickname": "saa",
//                      "avatarSrc": "",
//                      "owner": false,
//                      "steamId": "76561199411823892",
//                      "ban": null
//                    }
//                  ],
//                  "length": "LONG",
//                  "map": [
//                    "INFERNO"
//                  ]
//                }""");
//        return kafkaMessage;
//    }
//
//    public String getMessage(){
//        return "{\n" +
//                "    \"event\": {\n" +
//                "      \"matchID\": \"123\",\n" +
//                "      \"tick\": 121975,\n" +
//                "      \"winner\": \"team1\",\n" +
//                "      \"score_team2\": 16,\n" +
//                "      \"type\": \"get5_map_result\",\n" +
//                "      \"score_team1\": 0,\n" +
//                "      \"player_scores\": [\n" +
//                "        {\n" +
//                "          \"score\": 4,\n" +
//                "          \"client\": 1,\n" +
//                "          \"steamid\": \"76561199411823892\",\n" +
//                "          \"deaths\": 15,\n" +
//                "          \"assists\": 2,\n" +
//                "          \"kills\": 1,\n" +
//                "          \"mvp\": 2\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"score\": 24,\n" +
//                "          \"client\": 2,\n" +
//                "          \"steamid\": \"76561198073239050\",\n" +
//                "          \"deaths\": 3,\n" +
//                "          \"assists\": 0,\n" +
//                "          \"kills\": 10,\n" +
//                "          \"mvp\": 11\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"score\": 24,\n" +
//                "          \"client\": 3,\n" +
//                "          \"steamid\": \"76561198355147054\",\n" +
//                "          \"deaths\": 4,\n" +
//                "          \"assists\": 1,\n" +
//                "          \"kills\": 10,\n" +
//                "          \"mvp\": 11\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  }";
//    }
//
//}
