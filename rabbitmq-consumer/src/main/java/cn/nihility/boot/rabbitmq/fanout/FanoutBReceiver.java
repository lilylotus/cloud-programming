package cn.nihility.boot.rabbitmq.fanout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;

import java.util.Map;

//@Service
//@RabbitListener(queues = {FanoutRabbitmqConfiguration.FANOUT_B})
public class FanoutBReceiver {

    private static final Logger log = LoggerFactory.getLogger(FanoutBReceiver.class);

    @RabbitHandler
    public void receiver(Map<String, Object> dataMap) {
        log.info("Fanout [{}] Queue receiver [{}]", FanoutRabbitmqConfiguration.FANOUT_B, dataMap);
    }

}
