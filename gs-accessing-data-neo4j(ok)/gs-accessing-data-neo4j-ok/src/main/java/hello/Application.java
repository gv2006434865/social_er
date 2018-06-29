
package hello;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import tree.TreeSet;

@SpringBootApplication
@EnableNeo4jRepositories
public class Application {

	//private final static Logger log = LoggerFactory.getLogger(Application.class);
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
		System.out.println("111111111");
		//new TreeSet();		
	}
	
	/*@Bean
	CommandLineRunner demo(FBPersonRepository personRepository) {
		return args -> {
		personRepository.deleteAll();
		};
	}*/
}