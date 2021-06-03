package cn.nihility.boot.rabbitmq.fanout;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutRabbitConfig {

    /*
     *  创建三个队列 ：fanout.A   fanout.B  fanout.C
     *  将三个队列都绑定在交换机 fanoutExchange 上
     *  因为是扇型交换机, 路由键无需配置,配置也不起作用
     */

    @Bean
    public Queue fanoutAQueue() {
        return new Queue("fanout.A");
    }

    @Bean
    public Queue fanoutBQueue() {
        return new Queue("fanout.B");
    }

    @Bean
    public Queue fanoutCQueue() {
        return new Queue("fanout.C");
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
