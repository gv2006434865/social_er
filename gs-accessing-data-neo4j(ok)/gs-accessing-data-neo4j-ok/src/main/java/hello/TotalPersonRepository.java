package hello;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

public interface TotalPersonRepository extends CrudRepository<TotalUser, Long> {

	TotalUser findByName(String name);
	
	//@Query("MATCH (n) WHERE n.uid = {0} RETURN n")
	TotalUser findByUid(String uid);
}
