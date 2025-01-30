package com.example.my_app;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class rabbitMqConfig {

    @Bean
    public Queue myQueue() {
        return new Queue("spring-boot3", true);  // Red koji će primati poruke
    }

    @Bean
    public FanoutExchange adExchange() {
        return new FanoutExchange("ad_exchange");  // Razmena za slanje poruka
    }

    @Bean
    public Binding binding(@Qualifier("myQueue") Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);  
    }
}
