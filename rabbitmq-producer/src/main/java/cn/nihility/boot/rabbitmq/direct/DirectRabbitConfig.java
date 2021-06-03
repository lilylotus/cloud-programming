package cn.nihility.boot.rabbitmq.direct;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * direct exchange(直连型交换机)
 */
@Configuration
public class DirectRabbitConfig {

    public static final String DEAD_LETTER_EXCHANGE = "deadLetterExchange";

    // 普通队列/交换机 JSON
    @Bean
    public DirectExchange normalJsonDirectExchange() {
        return new DirectExchange("normalJsonDirectExchange");
    }

    @Bean
    public Queue normalJsonDirectQueue() {
        return new Queue("normalJsonDirectQueue", true, false, false);
    }

    @Bean
    public Binding normalJsonDirectExchangeBinding() {
        return BindingBuilder.bind(normalJsonDirectQueue()).to(normalJsonDirectExchange()).with("normalJsonDirectQueueRoutingKey");
    }

    // 普通队列/交换机
    @Bean
    public DirectExchange normalDirectExchange() {
        return new DirectExchange("normalDirectExchange");
    }

    @Bean
    public Queue normalDirectQueue() {
        return new Queue("normalDirectQueue", true, false, false);
    }

    @Bean
    public Binding normalDirectExchangeBinding() {
        return BindingBuilder.bind(normalDirectQueue()).to(normalDirectExchange()).with("normalDirectQueueRoutingKey");
    }

    // 声明死信交换机
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Queue deadLetterQueue() {
        /* durable:是否持久化,默认是 false, 持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
           exclusive: 默认也是 false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于 durable
           autoDelete: 是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
         */
        return new Queue("deadLetterQueue", true);
    }

    @Bean
    public Binding deadLetterExchangeBindQueue() {
        // 将队列和交换机绑定, 并设置用于匹配键：deadLetterQueueRoutingKey
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with("deadLetterQueueRoutingKey");
    }


    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange", true, false);
    }

    // 声明业务队列
    @Bean
    public Queue directQueue() {
        Map<String, Object> args = new HashMap<>(2);
        // x-dead-letter-exchange 这里声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        // x-dead-letter-routing-key 这里声明当前队列的死信路由key
        args.put("x-dead-letter-routing-key", "deadLetterQueueRoutingKey");
        return new Queue("directQueue", true, false, false, args);
    }

    @Bean
    public Binding directExchangeBindQueue() {
        // 将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("directExchangeRoutingKey");
    }

    @Bean
    public DirectExchange lonelyDirectExchange() {
        return new DirectExchange("lonelyDirectExchange");
    }

}
