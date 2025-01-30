package rs.ac.uns.ftn.informatika.jpa.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue myQueue() {
        return new Queue("spring-boot1", true); 
    }

    @Bean
    public Queue myQueue2() {
        return new Queue("spring-boot2", true);
    }
    
    @Bean
    public Queue myQueue3() {
        return new Queue("spring-boot3", true);
    }
    
    @Bean
    public FanoutExchange adExchange() {
        return new FanoutExchange("ad_exchange");
    }
    
    @Bean
    public Binding binding(@Qualifier("myQueue3") Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange); // Prazan routing key
    }
}