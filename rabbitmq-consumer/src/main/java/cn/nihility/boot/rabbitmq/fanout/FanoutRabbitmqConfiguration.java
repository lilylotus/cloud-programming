package cn.nihility.boot.rabbitmq.fanout;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutRabbitmqConfiguration {

    public static final String FANOUT_A = "fanout.A";
    public static final String FANOUT_B = "fanout.B";
    public static final String FANOUT_C = "fanout.C";

    @Bean
    public Queue fanoutAQueue() {
        return new Queue(FANOUT_A);
    }

    @Bean
    public Queue fanoutBQueue() {
        return new Queue(FANOUT_B);
    }

    @Bean
    public Queue fanoutCQueue() {
        return new Queue(FANOUT_C);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    public Binding bindingFanoutExchangeAQueue() {
        return BindingBuilder.bind(fanoutAQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingFanoutExchangeBQueue() {
        return BindingBuilder.bind(fanoutBQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingFanoutExchangeCQueue() {
        return BindingBuilder.bind(fanoutCQueue()).to(fanoutExchange());
    }

}
