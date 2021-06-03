package cn.nihility.boot.rabbitmq.direct;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class DirectReceiver {

    private static final Logger log = LoggerFactory.getLogger(DirectReceiver.class);

    @RabbitListener(queues = {"directQueue"})
    public void receiver(Map<String, Object> dataMap, Message message, Channel channel) throws IOException {

        log.info("directQueue receiver [{}]", dataMap);

        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        log.info("deliveryTag [{}]", deliveryTag);

        if (deliveryTag % 2 == 0) {
            log.error("消息消费发生异常，basicNack - tag [{}]", deliveryTag);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        } else {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    @RabbitListener(queues = {"deadLetterQueue"})
    public void deadLetterQueueReceiver(Map<String, Object> dataMap, Message message, Channel channel) throws IOException {
        log.info("deadLetterQueue receiver [{}]", dataMap);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = {"normalDirectQueue"})
    public void normalDirectQueueReceiver(Map<String, Object> dataMap, Message message, Channel channel) throws IOException {
        log.info("normalDirectQueue receiver [{}]", dataMap);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        log.info("deliveryTag [{}]", deliveryTag);

        if (deliveryTag % 3 == 0) {
            log.error("消息消费发生异常 false，normalDirectQueue basicNack false - tag [{}]", deliveryTag);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        } else if (deliveryTag % 3 == 1) {
            log.error("消息消费发生异常 true，normalDirectQueue basicNack true - tag [{}]", deliveryTag);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        } else {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

}
