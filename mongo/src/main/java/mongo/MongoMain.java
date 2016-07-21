package mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MongoMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ConfigurableApplicationContext configContext = SpringApplication.run(MongoMain.class);
		
		System.out.println("\n\n" + configContext.getBeanDefinitionCount() + " \n\n");

	}

}
