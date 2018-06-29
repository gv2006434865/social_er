package hello;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.jmx.JmxException;

import tree.TreeSet2;

@SpringBootApplication
@EnableNeo4jRepositories
public class b_ReadFBData {
	FbUser UserNode2, UserNode3;
	TotalPersonRepository TotalPersonRepository;

	@Bean
	CommandLineRunner ReadFBData(FBPersonRepository personRepository) {
		return args -> {
			System.out.println("FB_FB_FB_FB_FB_FB_FB_FB");/*
			personRepository.deleteAll();
			
			List<FbUser> Facebook_List = new ArrayList<FbUser>();

			String[] friendinfo = JsonfileToString("fb_friendinfo.json"); //讀取資料
			String[] userinfo = JsonfileToString("fb_userinfo.json");

			try {
				JSONObject user = (JSONObject) new JSONParser().parse(userinfo[0]); //Object 轉 JsonObject
				FbUser UserNode = new FbUser("fb", (String) user.get("Uid"), (String) user.get("Uname"),
						(String) user.get("Email"), (String) user.get("Location"), (String) user.get("Birthday"), (String) user.get("Gender"));
				personRepository.save(UserNode);
				Facebook_List.add(UserNode);
				this.UserNode2 = personRepository.findByName(UserNode.getName());
				try {
					for (int j = 0; j < friendinfo.length; j++) {
						try {
							JSONObject friend = (JSONObject) new JSONParser().parse(friendinfo[j]);
							FbUser FriendNode = new FbUser("fb", (String) friend.get("Uid"),
									(String) friend.get("Uname"), (String) friend.get("Email"),
									(String) friend.get("Location"), (String) friend.get("Birthday"), 
									(String) friend.get("Gender"));
							Facebook_List.add(FriendNode);
							personRepository.save(FriendNode);
							UserNode.friendWith(FriendNode);
							
							personRepository.save(UserNode);
						} catch (Exception e) {
							continue; // go to the top of the for loop
						}
					}
				} catch (JmxException e) {
					e.printStackTrace();
				}
				
			} catch (JmxException e) {
				e.printStackTrace();
			}*/
			

			new TreeSet2(personRepository);
			System.out.println("呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵");
		};
		

	}

	String[][] toList(List<FbUser> t) {
		String[][] st = new String[t.size()][4];
		for (int i = 0; i < t.size(); i++) {
			st[i][0] = t.get(i).getName();
			st[i][1] = t.get(i).getEmail();
			st[i][2] = t.get(i).getLocation();
			st[i][3] = t.get(i).getBirthday();
		}
		return st;
	}

	// JSONArray arrays = (JSONArray) person.get("cars");
	// for (Object object : arrays) {
	// System.out.println("cars::::" + object);
	// }

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
