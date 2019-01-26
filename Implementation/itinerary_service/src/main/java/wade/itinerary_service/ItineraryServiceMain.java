package wade.itinerary_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;

import com.netflix.discovery.EurekaClient;

import wade.controller.ItineraryController;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = { "wade.repository"})
public class ItineraryServiceMain extends ItineraryController {
	@Autowired
	@Lazy
	private EurekaClient eurekaClient;

	@Value("${spring.application.name}")
	private String appName;

	public static void main(String[] args) {
		SpringApplication.run(ItineraryServiceMain.class, args);
	}

}
