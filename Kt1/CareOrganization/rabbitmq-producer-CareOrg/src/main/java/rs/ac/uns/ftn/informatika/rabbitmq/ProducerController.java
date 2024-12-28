package rs.ac.uns.ftn.informatika.rabbitmq;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api")
@CrossOrigin(origins = "http://localhost:4500")
public class ProducerController {
	
	@Autowired
	private Producer producer;
	
	/*
	@PostMapping(value="/{queue}", consumes = "text/plain")
	public ResponseEntity<String> sendMessage(@PathVariable("queue") String queue, @RequestBody String message) {
		producer.sendTo(queue, message);
		return ResponseEntity.ok().build();
	}*/
	@CrossOrigin(origins = "http://localhost:4500")
	@PostMapping(value="/{exchange}/{queue}", consumes = "text/plain")
	public ResponseEntity<String> sendMessageToExchange(@PathVariable("exchange") String exchange, @PathVariable("queue") String queue, @RequestBody String message) {
		producer.sendToExchange(exchange, queue, message);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = "/sendMessage", consumes = "application/json")
	public ResponseEntity<Map<String, String>> sendMessageFromFrontend(@RequestBody MessageRequest messageRequest) {
	    // Generiši ID za poruku (na primer, koristeći UUID)
		String messageId = UUID.randomUUID().toString();

	    // Kreiranje Message objekta sa potrebnim podacima
	    Message message = new Message();
	    message.setId(messageId);
	    message.setNaziv(messageRequest.getNaziv());
	    message.setLatitude(messageRequest.getLatitude());
	    message.setLongitude(messageRequest.getLongitude());
	    
	    // Postavi routingKey za slanje poruke u odgovarajući queue
	    String routingKey = "spring-boot1";  // Ovo je predefinisani routing key koji koristiš

	    // Pošaljite poruku preko Producer-a
	    producer.sendTo(routingKey, message); 
	    
	    Map<String, String> response = new HashMap<>();
	    response.put("status", "success");
	    response.put("message", "Message sent successfully");

	    return ResponseEntity.ok(response);
	}


}
