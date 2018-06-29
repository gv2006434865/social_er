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
public class TotalUser {

	@Id @GeneratedValue private Long id;

	private String social,uid,name,location,email,birthday,gender;

	private TotalUser() {
		// Empty constructor required as of Neo4j API 2.0.5
	};

	public TotalUser(String social, String uid, String name, String email, String location, String birthday, String gender) {
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
	public Set<TotalUser> friends;
	
	@Relationship(type = "Tracking", direction = Relationship.OUTGOING)
	public Set<TotalUser> trackers;
	
	@Relationship(type = "Following", direction = Relationship.INCOMING)
	public Set<TotalUser> followers;

	public void friendWith(TotalUser person) {
		if (friends == null) friends = new HashSet<>();
		friends.add(person);
	}
	public void Tracking(TotalUser person) {
		if (trackers == null) trackers = new HashSet<>();
		trackers.add(person);
	}
	public void Following(TotalUser person) {
		if (followers == null) followers = new HashSet<>();
		followers.add(person);
	}
	
	public Set<TotalUser> getFRIENDS() { return friends; }
	public Set<TotalUser> getTracking() { return trackers; }
	public Set<TotalUser> getFollowing() { return followers; }
	
	public void setFRIENDS(Set<TotalUser> a) { this.friends = a;}
	public void setTracking(Set<TotalUser> a) { this.trackers = a; }
	public void setFollowing(Set<TotalUser> a) { this.followers = a; }
	

	public String friendstoString() {
		return this.name + "'s friends => "
			+ Optional.ofNullable(this.friends)
			.orElse(Collections.emptySet()).stream().map(TotalUser::getName).collect(Collectors.toList());
	}
	public String trackerstoString() {
		return this.name + "'s trackerss => "
			+ Optional.ofNullable(this.trackers)
			.orElse(Collections.emptySet()).stream().map(TotalUser::getName).collect(Collectors.toList());
	}
	public String followerstoString() {
		return this.name + "'s followers => "
			+ Optional.ofNullable(this.followers)
			.orElse(Collections.emptySet()).stream().map(TotalUser::getName).collect(Collectors.toList());
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
	public void setSocial(String social) { this.social = social;; }
	public void setGender(String gender) { this.gender = gender;; }
}
