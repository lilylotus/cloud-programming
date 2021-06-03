package cn.nihility.boot.rabbitmq.topic;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/topic")
public class TopicController {

    private RabbitTemplate rabbitTemplate;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TopicController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RequestMapping("/sender/first")
    public Map<String, Object> topicFirstSender() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("messageId", UUID.randomUUID().toString());
        dataMap.put("messageData", "Topic Exchange Bing First Queue");
        dataMap.put("createTime", DATE_TIME_FORMATTER.format(LocalDateTime.now()));

        rabbitTemplate.convertAndSend("topicExchange", TopicRabbitConfig.TOPIC_ONE, dataMap);

        return dataMap;
    }

    @RequestMapping("/sender/second")
    public Map<String, Object> topicSecondSender() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("messageId", UUID.randomUUID().toString());
        dataMap.put("messageData", "Topic Exchange Bing Second Queue");
        dataMap.put("createTime", DATE_TIME_FORMATTER.format(LocalDateTime.now()));

        rabbitTemplate.convertAndSend("topicExchange", TopicRabbitConfig.TOPIC_TWO, dataMap);

        return dataMap;
    }

}
