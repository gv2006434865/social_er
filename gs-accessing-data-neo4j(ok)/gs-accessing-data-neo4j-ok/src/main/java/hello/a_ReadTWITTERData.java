package hello;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStreamReader;
import java.io.FileInputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.jmx.JmxException;

import Integration.Integration;
import tree.TreeSet;
import tree.TreeSet2;

@SpringBootApplication
@EnableNeo4jRepositories
public class a_ReadTWITTERData {
	private final static Logger log = LoggerFactory.getLogger(Application.class);

	@Bean
	CommandLineRunner ReadTWITTERData(TWITTERPersonRepository personRepository) {
		return args -> {
			personRepository.deleteAll();
			List<TwitterUser> Twitter_List = new ArrayList<TwitterUser>();
			JSONParser parsers = new JSONParser();

			String[] trackerinfo = JsonfileToString("twitter_tracker.json");
			String[] followerinfo = JsonfileToString("twitter_follower.json");
			String[] userinfo = JsonfileToString("twitter_userinfo.json");

			try {
				JSONObject user = (JSONObject) new JSONParser().parse(userinfo[0]);
				TwitterUser UserNode = new TwitterUser("twitter", (String) user.get("Uid"), (String) user.get("Uname"),
						(String) user.get("Email"), (String) user.get("Location"), (String) user.get("Birthday"), (String) user.get("Gender"));
				personRepository.save(UserNode);
				Twitter_List.add(UserNode);
				UserNode = personRepository.findByName(UserNode.getName()); // System.out.println(UserNode.getName());
				
				List<String> trackers = new ArrayList<String>();
				List<String> followers = new ArrayList<String>();
				List<String> friends = new ArrayList<String>();

				if (trackerinfo != null && followerinfo != null) { // 先將追蹤關西分成三等份 (追蹤，朋友，被追蹤)
					for (int j = 0; j < followerinfo.length; j++) {
						try {
							JSONObject follower = (JSONObject) new JSONParser().parse(followerinfo[j]);
							followers.add((String) follower.get("Uid"));
						} catch (Exception e) {
							continue;
						}
					}
					for (int j = 0; j < trackerinfo.length; j++) {
						try {
							JSONObject tracker = (JSONObject) new JSONParser().parse(trackerinfo[j]);
							trackers.add((String) tracker.get("Uid"));
						} catch (Exception e) {
							continue;
						}
					}
					// System.out.println(followers);System.out.println(trackers);
					for (int j = 0; j < trackerinfo.length; j++) {
						for (int k = 0; k < followerinfo.length; k++) {
							try {
								String st = trackers.get(j);
								String sf = followers.get(k);
								if (st.equals(sf))
									friends.add(sf);
							} catch (Exception e) {
								continue;
							}
						}
					}
					// System.out.println(friends);

					List<String> just_follower = new ArrayList<String>(followers);
					just_follower.removeAll(friends); // System.out.println(just_follower);
					List<String> just_tracker = new ArrayList<String>(trackers);
					just_tracker.removeAll(friends); // System.out.println(just_tracker);

					for (int j = 0; j < followerinfo.length; j++) {
						try {
							JSONObject follower = (JSONObject) new JSONParser().parse(followerinfo[j]);
							for (Object k : just_follower) { // 建立追蹤者節點
								if (k.equals((String) follower.get("Uid"))) {
									TwitterUser fn = CreatNode(follower);
									Twitter_List.add(fn);
									personRepository.save(fn);
									UserNode.Following(fn);
									personRepository.save(UserNode);
								}
							}
							for (Object k : friends) {
								if (k.equals((String) follower.get("Uid"))) { // 建立朋友節點
									TwitterUser fn = CreatNode(follower);
									Twitter_List.add(fn);
									personRepository.save(fn);
									UserNode.friendWith(fn);
									personRepository.save(UserNode);
								}
							}
						} catch (Exception e) {
							continue;
						}
					}
					for (int j = 0; j < trackerinfo.length; j++) {
						try {
							JSONObject tracker = (JSONObject) new JSONParser().parse(trackerinfo[j]);
							for (Object k : just_tracker) {
								if (k.equals((String) tracker.get("Uid"))) {
									TwitterUser tn = CreatNode(tracker);
									Twitter_List.add(tn);
									personRepository.save(tn);
									UserNode.Tracking(tn);
									personRepository.save(UserNode);
								}
							}
						} catch (Exception e) {
							continue;
						}
					}
					// ===================================================================================================
				} else if (trackerinfo == null) { // 僅建立追蹤者節點
					for (int j = 0; j < followerinfo.length; j++) {
						try {
							JSONObject follower = (JSONObject) new JSONParser().parse(followerinfo[j]);
							TwitterUser fn = CreatNode(follower);
							Twitter_List.add(fn);
							personRepository.save(fn);
							UserNode.Following(fn);
							personRepository.save(UserNode);
						} catch (Exception e) {
							continue;
						}
					}

				} else {
					for (int j = 0; j < trackerinfo.length; j++) {
						try {
							JSONObject tracker = (JSONObject) new JSONParser().parse(trackerinfo[j]);
							TwitterUser tn = CreatNode(tracker);
							Twitter_List.add(tn);
							personRepository.save(tn);
							UserNode.Following(tn);
							personRepository.save(UserNode);
						} catch (Exception e) {
							continue;
						}
					}
				}

			} catch (JmxException e) {
				e.printStackTrace();
			}

			new TreeSet2(personRepository);

		};
	}

	TwitterUser CreatNode(JSONObject jsonobject) {
		TwitterUser CreatNode = new TwitterUser("twitter", (String) jsonobject.get("Uid"),
				(String) jsonobject.get("Uname"), (String) jsonobject.get("Email"), (String) jsonobject.get("Location"),
				(String) jsonobject.get("Birthday"), (String) jsonobject.get("Gender"));
		return CreatNode;
	}

	String[][] toList(List<TwitterUser> t) {
		String[][] st = new String[t.size()][4];
		for (int i = 0; i < t.size(); i++) {
			st[i][0] = t.get(i).getName();
			st[i][1] = t.get(i).getEmail();
			st[i][2] = t.get(i).getLocation();
			st[i][3] = t.get(i).getBirthday();
		}
		return st;
	}

	static String[] JsonfileToString(String a) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(a), "UTF-8"));
		String str;
		int i = 0;
		String[] to = new String[50];
		while ((str = in.readLine()) != null) {
			i++;
			if (i >= 1 && str != null && str != " ")
				to[i - 1] = str.replace("\t", "").replace("[", "").replace("]", "").replace("},", "}\n");
		}
		for (int j = 0; j < i; j++)
			to[j] = to[j + 1];
		return to;
	}
}
