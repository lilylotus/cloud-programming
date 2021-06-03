package cn.nihility.boot.rabbitmq.confirm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfirmConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RabbitmqConfirmConfiguration.class);

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        // 这里明明设置了4095，但是项目运行之后，压测之后，还是会报异常，而且报异常的时候，RabbitMQ
        // web 管理页面上的 channel 数依旧是 2047，不得已只能分析源码了
        //CachingConnectionFactory cachingConnectionFactory = (CachingConnectionFactory) connectionFactory;
        //cachingConnectionFactory.getRabbitConnectionFactory().setRequestedChannelMax(4095);
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

    @Bean
    public RabbitTemplate rabbitTemplateJson(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启 Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

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
