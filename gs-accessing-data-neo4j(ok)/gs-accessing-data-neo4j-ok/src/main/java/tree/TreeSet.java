package tree;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;

import com.google.common.collect.Lists;

import Integration.Integration;
import Integration.Integration2;
import hello.FBPersonRepository;
import hello.FbUser;
import hello.TWITTERPersonRepository;
import hello.TotalUser;
import hello.TotalPersonRepository;
import hello.TwitterUser;

public class TreeSet {

	private String social, uid, name, location, email, birthday;

	List<FbUser> fblist = new ArrayList<FbUser>();
	List<String> fbnamelist = new ArrayList<String>();
	List<String> fblocationlist = new ArrayList<String>();
	List<String> fbemaillist = new ArrayList<String>();
	List<String> fbbirthdaylist = new ArrayList<String>();
	List<String> fbuidlist = new ArrayList<String>();

	List<TwitterUser> twlist = new ArrayList<TwitterUser>();
	List<String> twnamelist = new ArrayList<String>();
	List<String> twlocationlist = new ArrayList<String>();
	List<String> twemaillist = new ArrayList<String>();
	List<String> twbirthdaylist = new ArrayList<String>();
	List<String> twuidlist = new ArrayList<String>();
	static FBPersonRepository personRepository1;
	static TWITTERPersonRepository personRepository2;
	static TotalPersonRepository personRepository3;
	String[][] ans_return;
	List<TwitterUser> FollowingList2, TrackingList2, FRIENDSList2;

	public TreeSet(FBPersonRepository personRepository1) {
		this.personRepository1 = personRepository1;
		Iterable<FbUser> fp = personRepository1.findAll(); // 從personRepository取得之前存入Neo4j的資料
		for (FbUser user : fp) {
			fblist.add(user);
			fbnamelist.add(user.getName());
			fblocationlist.add(user.getLocation());
			fbemaillist.add(user.getEmail());
			fbbirthdaylist.add(user.getBirthday());
			fbuidlist.add(user.getUid());
		}
		System.out.println("呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵");
		String[][] st = new String[fbnamelist.size()][5];
		for (int i = 0; i < fbnamelist.size(); i++) {
			st[i][0] = fbnamelist.get(i);
			st[i][1] = fbemaillist.get(i);
			st[i][2] = fblocationlist.get(i);
			st[i][3] = fbbirthdaylist.get(i);
			st[i][4] = fbuidlist.get(i);
		}
		String[][] ans = new Ranking(fbnamelist, fbemaillist, fblocationlist, fbbirthdaylist, fbuidlist).getRanking();
		Integration Fb_In = new Integration(st, ans); // 將資料跟屬性排序丟入，計算結果
		ans_return = ans;
		String[][] name = Fb_In.getCombineName();
		String[][] email = Fb_In.getCombineEmail();
		String[][] location = Fb_In.getCombineLocation();
		String[][] birthday = Fb_In.getCombineBirthday();
		String[][] uid = Fb_In.getCombineUid();

		FbUser UserNode, UserNode2;
		float[][] FB_Score = Fb_In.getCombineScore(); // 更新與刪除資料
		// System.out.println(FB_Score.length +"/" + st.length);
		int a = FB_Score.length;
		for (int i = 0; i < FB_Score.length; i++) {
			for (int j = 0; j < a - 1; j++) {
				// System.out.println("FB_Score : "+FB_Score[i][j] + "/" + name[i][j] );
				if (FB_Score[i][j] >= 0.8) {
					// System.out.println(name[i][j] + "/" +FB_Score[i][j]);
					UserNode = personRepository1.findByName(split_line(name[i][j])); // 找到第一個帳號
					UserNode.setName(name[i][j]); // 將第一個帳號與第二個帳號存成一個帳號
					UserNode.setEmail(email[i][j]); // 更新第一個帳號
					UserNode.setLocation(location[i][j]);
					UserNode.setBirthday(birthday[i][j]);
					UserNode.setUid(uid[i][j]);
					personRepository1.save(UserNode);
					// System.out.println(UserNode.getName());
					// 刪除第二帳號
					UserNode2 = personRepository1.findByName(split_line2(name[i][j]));
					// System.out.println(UserNode2.getName());
					personRepository1.delete(UserNode2);
				}
			}
			a--;
			// System.out.println("------------");
		}
		UserNode = personRepository1.findByName("Frank");

	}

	public TreeSet(TWITTERPersonRepository personRepository2) {
		this.personRepository2 = personRepository2;
		Iterable<TwitterUser> tp = personRepository2.findAll();
		for (TwitterUser user : tp) {
			twlist.add(user);
			twnamelist.add(user.getName());
			twlocationlist.add(user.getLocation());
			twemaillist.add(user.getEmail());
			twbirthdaylist.add(user.getBirthday());
			twuidlist.add(user.getUid());
		}
		System.out.println("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
		String[][] st = new String[twnamelist.size()][5];
		for (int i = 0; i < twnamelist.size(); i++) {
			st[i][0] = twnamelist.get(i);
			st[i][1] = twemaillist.get(i);
			st[i][2] = twlocationlist.get(i);
			st[i][3] = twbirthdaylist.get(i);
			st[i][4] = twuidlist.get(i);
		}
		String[][] ans = new Ranking(twnamelist, twemaillist, twlocationlist, twbirthdaylist, twuidlist).getRanking();
		Integration Twitter_In = new Integration(st, ans);
		ans_return = ans;
		String[][] name = Twitter_In.getCombineName();
		String[][] email = Twitter_In.getCombineEmail();
		String[][] location = Twitter_In.getCombineLocation();
		String[][] birthday = Twitter_In.getCombineBirthday();
		String[][] uid = Twitter_In.getCombineUid();

		TwitterUser UserNode, UserNode2;
		float[][] Twitter_Score = Twitter_In.getCombineScore();
		// System.out.println(Twitter_Score.length +"/" + st.length);
		int a = Twitter_Score.length;
		for (int i = 0; i < Twitter_Score.length; i++) {
			for (int j = 0; j < a - 1; j++) {
				// System.out.println("Twitter_Score : "+Twitter_Score[i][j] + "/" +
				// name[i][j]);
				if (Twitter_Score[i][j] >= 0.8) {

					System.out.println(uid[i][j]);
					UserNode = personRepository2.findByName(split_line(name[i][j])); // 找到第一個帳號
					UserNode.setName(name[i][j]); // 將第一個帳號與第二個帳號存成一個帳號
					UserNode.setEmail(email[i][j]); // 更新第一個帳號
					UserNode.setLocation(location[i][j]);
					UserNode.setBirthday(birthday[i][j]);
					UserNode.setUid(uid[i][j]);
					personRepository2.save(UserNode);
					// 刪除第二帳號
					UserNode2 = personRepository2.findByName(split_line2(name[i][j]));
					// System.out.println(UserNode2.getName());
					personRepository2.delete(UserNode2);
				}
			}
			a--;
		}
	}

	public TreeSet(TotalPersonRepository personRepository3) {
		this.personRepository3 = personRepository3;
		personRepository3.deleteAll();
		System.out.println("TreeSet()");
		Iterable<FbUser> fp = this.personRepository1.findAll();
		Iterable<TwitterUser> tp = this.personRepository2.findAll();

		List<String> namelist = new ArrayList<String>();
		List<String> locationlist = new ArrayList<String>();
		List<String> emaillist = new ArrayList<String>();
		List<String> birthdaylist = new ArrayList<String>();
		List<String> uidlist = new ArrayList<String>();

		FbUser Node = personRepository1.findByName("陳芊安");

		TotalUser MainUserNode = new TotalUser("fb", Node.getUid(), Node.getName(), Node.getEmail(), Node.getLocation(),
				Node.getBirthday(), Node.getGender());
		personRepository3.save(MainUserNode);
		for (FbUser user : fp) {
			namelist.add(user.getName());
			locationlist.add(user.getLocation());
			emaillist.add(user.getEmail());
			birthdaylist.add(user.getBirthday());
			uidlist.add(user.getUid());
			if (user.getUid().equals(MainUserNode.getUid()))
				continue;
			TotalUser UserNode = new TotalUser("fb", user.getUid(), user.getName(), user.getEmail(), user.getLocation(),
					user.getBirthday(),user.getGender());
			personRepository3.save(UserNode);
			MainUserNode.friendWith(UserNode);
			personRepository3.save(MainUserNode);
		}
		List<TwitterUser> a = Lists.newArrayList(personRepository2.findAll().iterator());
		String[] aa = new String[a.size()];
		String aaa = null;
		for (int i = 0; i < a.size(); i++) {
			float result1 = StringMetrics.jaroWinkler().compare("陳芊安", a.get(i).getName());
			if (result1 > 0.8)
				aaa = a.get(i).getName();
		}
		TwitterUser Node2 = personRepository2.findByName(aaa);
		TotalUser MainUserNode2 = new TotalUser("twitter", Node2.getUid(), Node2.getName(), Node2.getEmail(),
				Node2.getLocation(), Node2.getBirthday(), Node2.getGender());
		personRepository3.save(MainUserNode);

		List<TwitterUser> FollowingList2 = Lists.newArrayList(Node2.getFollowing().iterator());
		List<TwitterUser> TrackingList2 = Lists.newArrayList(Node2.getTracking().iterator());
		List<TwitterUser> FRIENDSList2 = Lists.newArrayList(Node2.getFRIENDS().iterator());

		String[] TwitterUserFriends = new String[FRIENDSList2.size()];
		for (int i = 0; i < FRIENDSList2.size(); i++)
			TwitterUserFriends[i] = FRIENDSList2.get(i).getUid();

		String[] TwitterUserTracking = new String[TrackingList2.size()];
		for (int i = 0; i < TrackingList2.size(); i++)
			TwitterUserTracking[i] = TrackingList2.get(i).getUid();

		String[] TwitterUserFollowing = new String[FollowingList2.size()];
		for (int i = 0; i < FollowingList2.size(); i++)
			TwitterUserFollowing[i] = FollowingList2.get(i).getUid();

		for (TwitterUser user : tp) {
			namelist.add(user.getName());
			locationlist.add(user.getLocation());
			emaillist.add(user.getEmail());
			birthdaylist.add(user.getBirthday());
			uidlist.add(user.getUid());
			if (user.getUid().equals(MainUserNode2.getUid()))
				continue;
			TotalUser UserNode = new TotalUser("twitter", user.getUid(), user.getName(), user.getEmail(),
					user.getLocation(), user.getBirthday(), user.getGender());
			personRepository3.save(UserNode);
			for (int i = 0; i < TwitterUserFriends.length; i++) {
				if (user.getUid().equals(TwitterUserFriends[i])) {
					MainUserNode2.friendWith(UserNode);
					personRepository3.save(MainUserNode2);
				}
			}
			for (int i = 0; i < TwitterUserTracking.length; i++) {

				if (user.getUid().equals(TwitterUserTracking[i])) {
					MainUserNode2.Tracking(UserNode);
					personRepository3.save(MainUserNode2);
				}
			}
			for (int i = 0; i < TwitterUserFollowing.length; i++) {
				if (user.getUid().equals(TwitterUserFollowing[i])) {
					MainUserNode2.Following(UserNode);
					personRepository3.save(MainUserNode2);
				}
			}
		}
		System.out.println("嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿");
		String[][] st = new String[namelist.size()][5];
		for (int i = 0; i < namelist.size(); i++) {
			st[i][0] = namelist.get(i);
			st[i][1] = emaillist.get(i);
			st[i][2] = locationlist.get(i);
			st[i][3] = birthdaylist.get(i);
			st[i][4] = uidlist.get(i);
		}
		// 確認執行順序
		String[][] ans = new Ranking(namelist, emaillist, locationlist, birthdaylist, uidlist).getRanking();
		ans_return = ans;
		
		//for(int i=0;i<namelist.size();i++)System.out.println(st[i][0] + " / "+st[i][1]);
		
		
		Integration2 Total_In = new Integration2(st, ans);
		
		
		String[][] name = Total_In.getCombineName();
		String[][] email = Total_In.getCombineEmail();
		String[][] location = Total_In.getCombineLocation();
		String[][] birthday = Total_In.getCombineBirthday();
		String[][] uid = Total_In.getCombineUid();

		// 取得 fb 與 twitter 合併後最新的總名單來比對資訊
		List<TwitterUser> tw_finallist = Lists.newArrayList(personRepository2.findAll().iterator());
		String[] tw_finalarray = new String[tw_finallist.size()];
		for (int i = 0; i < tw_finallist.size(); i++) {
			tw_finalarray[i] = tw_finallist.get(i).getUid();
			// System.out.println("tw_finalarray[i] : " + tw_finalarray[i]);
		}

		List<FbUser> fb_finallist = Lists.newArrayList(personRepository1.findAll().iterator());
		String[] fb_finalarray = new String[fb_finallist.size()];
		for (int i = 0; i < fb_finallist.size(); i++) {
			fb_finalarray[i] = fb_finallist.get(i).getUid();
			// System.out.println("fb_finalarray[i] : " + fb_finalarray[i]);
		}
		
		//單向關係僅能從建立的起始點查看，無向與雙向才能結束與起始點都看到
		List<TotalUser> MainUserNode2_getFollowing = Lists.newArrayList(MainUserNode2.getFollowing().iterator());
		List<TotalUser> MainUserNode2_getTracking = Lists.newArrayList(MainUserNode2.getTracking().iterator());
		

		
		float[][] Total_Score = Total_In.getCombineScore();
		// System.out.println(Twitter_Score.length +"/" + st.length);
		int b = Total_Score.length;
		for (int i = 0; i < Total_Score.length; i++) {
			for (int j = 0; j < b - 1; j++) {
				// System.out.println("Twitter_Score : "+Twitter_Score[i][j] + "/" +
				// name[i][j]);
				if (Total_Score[i][j] >= 0.8) {
					// System.out.println("split_line(name[i][j]) : "+uid[i][j]);
					String[] FindNodeUid = new String[split_line_all(uid[i][j]).length];
					FindNodeUid = split_line_all(uid[i][j]);
					// for(int f=0; f<FindNodeUid.length;f++) System.out.println(FindNodeUid[f]);
					TotalUser UserNode = null, UserNode2;
					for (int h = 0; h < FindNodeUid.length; h++) {
						//從 FB 的名單中找出符合ID的節點做修改
						for (int p = 0; p < fb_finalarray.length; p++) { 
							if (StringMetrics.levenshtein().compare(FindNodeUid[h], fb_finalarray[p]) == 1) {
								/*System.out.println("FindNodeUid[h] : " + FindNodeUid[h]);
								System.out.println("fb_finalarray[p] : " + fb_finalarray[p]);
								System.out.println(name[i][j]);
								System.out.println(uid[i][j]);
								System.out.println("**************************************");*/
								UserNode = personRepository3.findByUid(fb_finalarray[p]); // 找到第一個帳號    //以FB為主，刪除TWUTTER那邊的節點
								UserNode.setName(name[i][j]); // 將第一個帳號與第二個帳號存成一個帳號
								UserNode.setEmail(email[i][j]); // 更新第一個帳號
								UserNode.setLocation(location[i][j]);
								UserNode.setBirthday(birthday[i][j]);
								UserNode.setUid(uid[i][j]);
								UserNode.setSocial("total");
								personRepository3.save(UserNode);
							}
						}
						//從 Twitter 的名單中找出符合ID的節點做刪除與關連重新連接
						
						for (int q = 0; q < tw_finalarray.length; q++) {
							if (StringMetrics.levenshtein()
									.compare(FindNodeUid[h], split_line(tw_finalarray[q])) == 1) {
								//System.out.println(personRepository3.findByUid(tw_finalarray[q]).getName());
								
								UserNode2 = personRepository3.findByUid(tw_finalarray[q]);
								/*System.out.println("UserNode2.getUid() : "+UserNode2.getUid());
								System.out.println("UserNode.getUid() : "+UserNode.getUid());*/
								if(UserNode2.getFRIENDS()!=null) {
									List<TotalUser> UserNode2_getFRIENDS = 
											Lists.newArrayList(UserNode2.getFRIENDS().iterator());
									for(int qq=0;qq<UserNode2_getFRIENDS.size();qq++) {
										/*System.out.println("UserNode2_getFRIENDS : ");
										System.out.println(UserNode2_getFRIENDS.get(qq).getName());
										System.out.println(UserNode2_getFRIENDS.get(qq).getUid());*/
										UserNode.friendWith(personRepository3.findByUid(UserNode2_getFRIENDS.get(qq).getUid()));
										personRepository3.save(UserNode);
									}
								}
								if(UserNode2.getFollowing()!=null) {
									List<TotalUser> UserNode2_getFollowing = 
											Lists.newArrayList(UserNode2.getFollowing().iterator());
									for(int qq=0;qq<UserNode2_getFollowing.size();qq++) {
										//System.out.println("UserNode2_getFollowing : ");
										UserNode.Following(personRepository3.findByUid(UserNode2_getFollowing.get(qq).getUid()));
										personRepository3.save(UserNode);
									}
								}
								if(UserNode2.getTracking()!=null) {
									List<TotalUser> UserNode2_getTracking = 
											Lists.newArrayList(UserNode2.getTracking().iterator());
									for(int qq=0;qq<UserNode2_getTracking.size();qq++) {
										//System.out.println("UserNode2_getTracking : ");
										UserNode.Tracking(personRepository3.findByUid(UserNode2_getTracking.get(qq).getUid()));
										personRepository3.save(UserNode);
									}
								}
								personRepository3.delete(UserNode2);
								//System.out.println("personRepository3.delete(UserNode2);");
							}
							
						}
						
					}
					System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				}
			}
			b--;
		}
		
		System.out.println("-----" + ans[0][0] + ans[0][1] + "/" + ans[1][0] + ans[1][1] + "/" + ans[2][0] + ans[2][1]
				+ "/" + ans[3][0] + ans[3][1] + "/" + ans[4][0] + ans[4][1]);

	}


	
	public String[][] getRank() {
		return ans_return;
	}

	String split_line(String a) {
		String line = a;
		String[] split_line = line.split(",");
		return split_line[0];
	}

	String split_line2(String a) {
		String line = a;
		String[] split_line = line.split(",");
		return split_line[1];
	}

	String[] split_line_all(String a) {
		String line = a;
		String[] split_line = line.split(",");
		return split_line;
	}
}
