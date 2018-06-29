package hello;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class TwitterUser {

	@Id @GeneratedValue private Long id;

	private String social,uid,name,location,email,birthday,gender;

	private TwitterUser() {
		// Empty constructor required as of Neo4j API 2.0.5
	};

	public TwitterUser(String social, String uid, String name, String email, String location, String birthday,String gender) {
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
	public Set<TwitterUser> friends;
	
	@Relationship(type = "Tracking", direction = Relationship.OUTGOING)
	public Set<TwitterUser> trackers;
	
	@Relationship(type = "Following", direction = Relationship.INCOMING)
	public Set<TwitterUser> followers;
	
	public Set<TwitterUser> getFRIENDS() { return friends; }
	public Set<TwitterUser> getTracking() { return trackers; }
	public Set<TwitterUser> getFollowing() { return followers; }
	

	public void friendWith(TwitterUser person) {
		if (friends == null) friends = new HashSet<>();
		friends.add(person);
	}
	public void Tracking(TwitterUser person) {
		if (trackers == null) trackers = new HashSet<>();
		trackers.add(person);
	}
	public void Following(TwitterUser person) {
		if (followers == null) followers = new HashSet<>();
		followers.add(person);
	}

	public String friendstoString() {
		return this.name + "'s friends => "
			+ Optional.ofNullable(this.friends)
			.orElse(Collections.emptySet()).stream().map(TwitterUser::getName).collect(Collectors.toList());
	}
	public String trackerstoString() {
		return this.name + "'s trackerss => "
			+ Optional.ofNullable(this.trackers)
			.orElse(Collections.emptySet()).stream().map(TwitterUser::getName).collect(Collectors.toList());
	}
	public String followerstoString() {
		return this.name + "'s followers => "
			+ Optional.ofNullable(this.followers)
			.orElse(Collections.emptySet()).stream().map(TwitterUser::getName).collect(Collectors.toList());
	}

	public String getName() { return name; }
	public String getSocial() { return social; }
	public String getUid() { return uid; }
	public String getEmail() { return email; }
	public String getLocation() { return location; }
	public String getBirthday() { return birthday; }
	public String getGender() { return gender; }
	public Set<TwitterUser> gettry1() { return followers; }

	public void setName(String name) {this.name = name;}
	public void setEmail(String email) {this.email = email;}
	public void setLocation(String location) {this.location = location;}
	public void setBirthday(String birthday) {this.birthday = birthday;}
	public void setUid(String uid) {this.uid = uid;}
}
