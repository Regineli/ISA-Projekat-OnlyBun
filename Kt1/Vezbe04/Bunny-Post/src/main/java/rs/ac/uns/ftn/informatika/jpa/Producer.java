package rs.ac.uns.ftn.informatika.jpa;

import java.util.Map;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import rs.ac.uns.ftn.informatika.jpa.model.BunnyPost;

@Component
public class Producer {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
    private ObjectMapper objectMapper;

    

	public void sendBunnyPost(BunnyPost bunnyPost, String username) {
        try {
            String message = objectMapper.writeValueAsString(Map.of(
                "username", username,
                "description", bunnyPost.getDetails(),
                "time", bunnyPost.getTime()
            ));


            rabbitTemplate.convertAndSend("ad_exchange", "", message);
            System.out.println("Poruka poslata: " + message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
