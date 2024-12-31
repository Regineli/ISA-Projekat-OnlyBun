package rs.ac.uns.ftn.informatika.jpa;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import rs.ac.uns.ftn.informatika.jpa.service.BunnyPostService;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class BunnyPostProject {

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BunnyPostProject.class, args);
	}
	
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
		System.out.println("Bean connected");
		return connectionFactory;
	}

}
