package cn.nihility.boot.rabbitmq.fanout;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/fanout")
public class FanoutController {

    private RabbitTemplate rabbitTemplate;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public FanoutController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RequestMapping("/sender")
    public Map<String, Object> directSender() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("messageId", UUID.randomUUID().toString());
        dataMap.put("messageData", "Fanout Exchange Bing Queue Data Message");
        dataMap.put("createTime", DATE_TIME_FORMATTER.format(LocalDateTime.now()));

        rabbitTemplate.convertAndSend("fanoutExchange", null, dataMap);

        return dataMap;
    }

}
