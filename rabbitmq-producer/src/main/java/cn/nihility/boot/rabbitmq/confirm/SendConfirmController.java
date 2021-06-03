package cn.nihility.boot.rabbitmq.confirm;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/confirm")
public class SendConfirmController {

    private RabbitTemplate rabbitTemplate;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SendConfirmController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RequestMapping("/exchange")
    public Map<String, Object> noExchange() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("messageId", UUID.randomUUID().toString());
        dataMap.put("messageData", "Direct Exchange Bing Queue");
        dataMap.put("createTime", DATE_TIME_FORMATTER.format(LocalDateTime.now()));

        rabbitTemplate.convertAndSend("noExchange", "directExchangeRoutingKey", dataMap);

        return dataMap;
    }

    @RequestMapping("/queue")
    public Map<String, Object> noQueue() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("messageId", UUID.randomUUID().toString());
        dataMap.put("messageData", "lonelyDirectExchange Exchange Bing Queue");
        dataMap.put("createTime", DATE_TIME_FORMATTER.format(LocalDateTime.now()));

        rabbitTemplate.convertAndSend("lonelyDirectExchange", "directExchangeRoutingKey", dataMap);

        return dataMap;
    }

}
