package wade.account_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.EurekaClient;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class EurekaClientApplication implements GreetingController{
	 @Autowired
	 @Lazy
	 private EurekaClient eurekaClient;
	 
	 @Value("${spring.application.name}")
	  private String appName;
	 
    public static void main( String[] args )
    {
    	SpringApplication.run(EurekaClientApplication.class, args);
    }
    
    public String greeting() {
        return String.format("Hello from '%s'!", eurekaClient.getApplication(appName).getName());
    }

	@Override
	public String mainPage() {
		return "This is AccountService main page. Welcome!";
	}
}
