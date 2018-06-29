package hello;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import tree.TreeSet2;

@SpringBootApplication
@EnableNeo4jRepositories
public class c_ReadTotalData {
	@Bean
	CommandLineRunner ReadTotalData(TotalPersonRepository personRepository) {
		return args -> {
			personRepository.deleteAll();
			new TreeSet2(personRepository);
		};
	}
}
