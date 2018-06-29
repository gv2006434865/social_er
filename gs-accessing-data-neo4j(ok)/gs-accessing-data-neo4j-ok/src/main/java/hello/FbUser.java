package hello;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class FbUser {

	@Id @GeneratedValue private Long id;

	private String social,name,location,email,birthday, gender;

	@Property(name = "uid")
	String uid;
	
	private FbUser() {
		// Empty constructor required as of Neo4j API 2.0.5
	};

	public FbUser(String social, String uid, String name, String email, String location, String birthday, String gender) {
		this.social = social;
		this.uid = uid;
		this.name = name;
		this.email = email;
		this.location = location;
		this.birthday = birthday;
		this.gender = gender;
	}

	/**
	 * Neo4j doesn't REALLY have bi-directional relationships. It just means when querying
	 * to ignore the direction of the relationship.
	 * https://dzone.com/articles/modelling-data-neo4j
	 */
	@Relationship(type = "FRIENDS", direction = Relationship.UNDIRECTED)
	public Set<FbUser> friends;
	public void friendWith(FbUser person) {
		if (friends == null) friends = new HashSet<>();
		friends.add(person);
	}
	
	public Set<FbUser> getfriends() { return friends; }

	public String fbtoString() {

		return this.name + "'s friends => "
			+ Optional.ofNullable(this.friends).orElse(
					Collections.emptySet()).stream()
						.map(FbUser::getName)
						.collect(Collectors.toList());
	}
	
	public String getName() { return name; }
	public String getSocial() { return social; }
	public String getUid() { return uid; }
	public String getEmail() { return email; }
	public String getLocation() { return location; }
	public String getBirthday() { return birthday; }
	public String getGender() { return gender; }

	public void setName(String name) {this.name = name;}
	public void setEmail(String email) {this.email = email;}
	public void setLocation(String location) {this.location = location;}
	public void setBirthday(String birthday) {this.birthday = birthday;}
	public void setUid(String uid) {this.uid = uid;}
}
