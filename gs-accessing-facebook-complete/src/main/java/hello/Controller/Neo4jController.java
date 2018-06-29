package hello.Controller;

import javax.inject.Inject;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/Neo4j")
public class Neo4jController {

   /* private Twitter twitter;

    private ConnectionRepository connectionRepository;

    @Inject
    public Neo4jController(Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
    }*/

    @RequestMapping(method=RequestMethod.GET)
    public String helloNeo4j() {
        return "Neo4j";
    }

}
