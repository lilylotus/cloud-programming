package cn.nihility.boot.rabbitmq.confirm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfirmConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RabbitmqConfirmConfiguration.class);

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启 Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("ConfirmCallback: 相关数据：[{}]", correlationData);
                log.info("ConfirmCallback: 确认情况：[{}}", ack);
                log.info("ConfirmCallback: 原因：[{}]", cause);
            }
        });

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("ReturnCallback: 消息体：[{}]", message);
                log.info("ReturnCallback: 回应码：[{}]", replyCode);
                log.info("ReturnCallback: 回应信息：[{}]", replyText);
                log.info("ReturnCallback: 交换机：[{}]", exchange);
                log.info("ReturnCallback: 路由键：[{}]", routingKey);
            }
        });

        return rabbitTemplate;
    }

}
