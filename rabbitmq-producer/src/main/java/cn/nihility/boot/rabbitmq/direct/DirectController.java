package cn.nihility.boot.rabbitmq.direct;

import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/direct")
public class DirectController {

    private RabbitTemplate rabbitTemplate;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public DirectController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RequestMapping("/sender")
    public Map<String, Object> directSender() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("messageId", UUID.randomUUID().toString());
        dataMap.put("messageData", "Direct Exchange Bing Queue");
        dataMap.put("createTime", DATE_TIME_FORMATTER.format(LocalDateTime.now()));

        rabbitTemplate.convertAndSend("directExchange", "directExchangeRoutingKey", dataMap);

        return dataMap;
    }

    @RequestMapping("/expire")
    public Map<String, Object> expire() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("messageId", UUID.randomUUID().toString());
        dataMap.put("messageData", "Direct Exchange Expire Bing Queue");
        dataMap.put("createTime", DATE_TIME_FORMATTER.format(LocalDateTime.now()));

        rabbitTemplate.convertAndSend("directExchange", "directExchangeRoutingKey", dataMap, message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            message.getMessageProperties().setExpiration("10000");
            return message;
        });

        return dataMap;
    }

    @RequestMapping("/normal")
    public Map<String, Object> normal() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("messageId", UUID.randomUUID().toString());
        dataMap.put("messageData", "Normal Exchange Bing Queue");
        dataMap.put("createTime", DATE_TIME_FORMATTER.format(LocalDateTime.now()));

        rabbitTemplate.convertAndSend("normalDirectExchange", "normalDirectQueueRoutingKey", dataMap);

        return dataMap;
    }

    @RequestMapping("/deadLetter")
    public Map<String, Object> deadLetter() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("messageId", UUID.randomUUID().toString());
        dataMap.put("messageData", "DeadLetter Exchange Bing Queue");
        dataMap.put("createTime", DATE_TIME_FORMATTER.format(LocalDateTime.now()));

        rabbitTemplate.convertAndSend("deadLetterExchange", "deadLetterQueueRoutingKey", dataMap);

        return dataMap;
    }

}
