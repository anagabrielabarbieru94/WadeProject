package wade.account_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.netflix.discovery.EurekaClient;

import wade.controller.AccountController;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"wade.repository", "wade.model"})
@EnableJpaRepositories("wade.repository")
@EntityScan(basePackages = "wade.model")
public class EurekaClientApplication extends AccountController{
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

}
