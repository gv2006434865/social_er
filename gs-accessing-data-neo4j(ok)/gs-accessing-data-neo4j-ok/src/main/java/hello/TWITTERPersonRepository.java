package hello;

import org.springframework.data.repository.CrudRepository;

public interface TWITTERPersonRepository extends CrudRepository<TwitterUser, Long> {

	TwitterUser findByName(String name);
	TwitterUser findByUid(String uid);
}
