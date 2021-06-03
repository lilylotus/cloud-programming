package cn.nihility.boot.rabbitmq.topic;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicRabbitConfig {

    public static final String TOPIC_ONE = "topic.one";
    public static final String TOPIC_TWO = "topic.two";

    @Bean
    public Queue topicFirstQueue() {
        return new Queue(TOPIC_ONE, true);
    }

    @Bean
    public Queue topicSecondQueue() {
        return new Queue(TOPIC_TWO, true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Binding bindingTopicExchangeFirstQueue() {
        return BindingBuilder.bind(topicFirstQueue()).to(topicExchange()).with(TOPIC_ONE);
    }

    @Bean
    public Binding bindingTopicExchangeSecondQueue() {
        return BindingBuilder.bind(topicSecondQueue()).to(topicExchange()).with("topic.#");
    }

}
