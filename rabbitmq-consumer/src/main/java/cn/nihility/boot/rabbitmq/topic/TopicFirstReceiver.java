package cn.nihility.boot.rabbitmq.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RabbitListener(queues = {"topic.one"})
public class TopicFirstReceiver {

    private static final Logger log = LoggerFactory.getLogger(TopicFirstReceiver.class);

    @RabbitHandler
    public void receiver(Map<String, Object> dataMap) {
        log.info("topic first receiver [{}]", dataMap);
    }

}
