package hello;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FBPersonRepository extends CrudRepository<FbUser, Long> {
    FbUser findByName(String name);
    FbUser findByUid(String uid);
}
