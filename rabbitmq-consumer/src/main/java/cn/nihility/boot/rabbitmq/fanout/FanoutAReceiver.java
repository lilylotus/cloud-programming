package cn.nihility.boot.rabbitmq.fanout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RabbitListener(queues = {FanoutRabbitmqConfiguration.FANOUT_A})
public class FanoutAReceiver {

    private static final Logger log = LoggerFactory.getLogger(FanoutAReceiver.class);

    @RabbitHandler
    public void receiver(Map<String, Object> dataMap) {
        log.info("Fanout [{}] Queue receiver [{}]", FanoutRabbitmqConfiguration.FANOUT_A, dataMap);
    }

}
