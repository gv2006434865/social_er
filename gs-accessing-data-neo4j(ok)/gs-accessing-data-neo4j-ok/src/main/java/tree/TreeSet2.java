package tree;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;

import com.google.common.collect.Lists;

import Integration.ER;
import Integration.Integration;
import Integration.Integration2;
import Integration.Iterative;
import hello.FBPersonRepository;
import hello.FbUser;
import hello.TWITTERPersonRepository;
import hello.TotalUser;
import hello.TotalPersonRepository;
import hello.TwitterUser;

public class TreeSet2 {

	private String social, uid, name, location, email, birthday;

	List<FbUser> fblist = new ArrayList<FbUser>();
	List<String> fbnamelist = new ArrayList<String>();
	List<String> fblocationlist = new ArrayList<String>();
	List<String> fbemaillist = new ArrayList<String>();
	List<String> fbbirthdaylist = new ArrayList<String>();
	List<String> fbuidlist = new ArrayList<String>();
	List<String> fbgenderlist = new ArrayList<String>();

	List<TwitterUser> twlist = new ArrayList<TwitterUser>();
	List<String> twnamelist = new ArrayList<String>();
	List<String> twlocationlist = new ArrayList<String>();
	List<String> twemaillist = new ArrayList<String>();
	List<String> twbirthdaylist = new ArrayList<String>();
	List<String> twuidlist = new ArrayList<String>();
	List<String> twgenderlist = new ArrayList<String>();
	static FBPersonRepository personRepository1;
	static TWITTERPersonRepository personRepository2;
	static TotalPersonRepository personRepository3;
	String[][] ans_return;
	List<TwitterUser> FollowingList2, TrackingList2, FRIENDSList2;

	public TreeSet2(FBPersonRepository personRepository1) {
		this.personRepository1 = personRepository1;
		Iterable<FbUser> fp = personRepository1.findAll(); // 從personRepository取得之前存入Neo4j的資料
		for (FbUser user : fp) {
			fblist.add(user);
			fbnamelist.add(user.getName());
			fblocationlist.add(user.getLocation());
			fbemaillist.add(user.getEmail());
			fbbirthdaylist.add(user.getBirthday());
			fbuidlist.add(user.getUid());
			fbgenderlist.add(user.getGender());
		}
		String[][] rank = new Ranking2(fbnamelist, fbemaillist, fblocationlist, fbbirthdaylist, fbuidlist, fbgenderlist)
				.getRank();
		int m=2,n=1,p=2;
		String[][] a = new Distinct_Tree(rank, personRepository1, m).get_Distinct_Tree();
		String[][] b = new Iterative(a, rank, personRepository1, n, m).get_Iterative();
		String[][] c = new ER(b, rank, personRepository1, p, m+n).get_ER();
		FbUser UserNode = null, UserNode2 = null;
		for (int i = 0; i < c.length; i++) {
			if (c[i][0] == null)
				break;
			UserNode = personRepository1.findByUid(c[i][0]); // 找到第一個帳號
			UserNode2 = personRepository1.findByUid(c[i][1]); // 找到第二個帳號
			UserNode.setName(combin(UserNode.getName(), UserNode2.getName()));
			UserNode.setEmail(combin(UserNode.getEmail(), UserNode2.getEmail()));
			UserNode.setLocation(combin(UserNode.getLocation(), UserNode2.getLocation()));
			UserNode.setBirthday(combin(UserNode.getBirthday(), UserNode2.getBirthday()));
			UserNode.setUid(combin(UserNode.getUid(), UserNode2.getUid()));
			personRepository1.save(UserNode);
			personRepository1.delete(UserNode2);// 刪除第二帳號
		}
	}

	public TreeSet2(TWITTERPersonRepository personRepository2) {
		this.personRepository2 = personRepository2;
		Iterable<TwitterUser> tp = personRepository2.findAll();
		for (TwitterUser user : tp) {
			twlist.add(user);
			twnamelist.add(user.getName());
			twlocationlist.add(user.getLocation());
			twemaillist.add(user.getEmail());
			twbirthdaylist.add(user.getBirthday());
			twuidlist.add(user.getUid());
			twgenderlist.add(user.getGender());
		}
		System.out.println("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
		String[][] rank = new Ranking2(twnamelist, twemaillist, twlocationlist, twbirthdaylist, twuidlist, twgenderlist)
				.getRank();
		int m=2,n=1,p=2;
		String[][] a = new Distinct_Tree(rank, personRepository2, m).get_Distinct_Tree();
		String[][] b = new Iterative(a, rank, personRepository2, n, m).get_Iterative();
		String[][] c = new ER(b, rank, personRepository2, p, m+n).get_ER();
		TwitterUser UserNode = null, UserNode2 = null;
		for (int i = 0; i < c.length; i++) {
			if (c[i][0] == null)
				break;
			UserNode = personRepository2.findByUid(c[i][0]); // 找到第一個帳號
			UserNode2 = personRepository2.findByUid(c[i][1]); // 找到第二個帳號
			UserNode.setName(combin(UserNode.getName(), UserNode2.getName()));
			UserNode.setEmail(combin(UserNode.getEmail(), UserNode2.getEmail()));
			UserNode.setLocation(combin(UserNode.getLocation(), UserNode2.getLocation()));
			UserNode.setBirthday(combin(UserNode.getBirthday(), UserNode2.getBirthday()));
			UserNode.setUid(combin(UserNode.getUid(), UserNode2.getUid()));
			personRepository2.save(UserNode);
			personRepository2.delete(UserNode2);// 刪除第二帳號
		}
	}

	public TreeSet2(TotalPersonRepository personRepository3) {
		this.personRepository3 = personRepository3;
		personRepository3.deleteAll();
		Iterable<FbUser> fp = this.personRepository1.findAll();
		Iterable<TwitterUser> tp = this.personRepository2.findAll();

		for (FbUser user : fp) {
			TotalUser UserNode = new TotalUser(user.getSocial(), user.getUid(), user.getName(), user.getEmail(),
					user.getLocation(), user.getBirthday(), user.getGender());
			personRepository3.save(UserNode);
		}
		for (FbUser user : fp) {
			TotalUser u = personRepository3.findByUid(user.getUid());
			Iterator<FbUser> f = user.getfriends().iterator();
			for (int i = 0; i < user.getfriends().size(); i++)
				u.friendWith(personRepository3.findByUid(f.next().getUid()));
			personRepository3.save(u);
		}
		for (TwitterUser user : tp) {
			TotalUser UserNode = new TotalUser(user.getSocial(), user.getUid(), user.getName(), user.getEmail(),
					user.getLocation(), user.getBirthday(), user.getGender());
			personRepository3.save(UserNode);
		}
		for (TwitterUser user : tp) {
			TotalUser u = personRepository3.findByUid(user.getUid());
			if (user.getFRIENDS() != null) {
				Iterator<TwitterUser> friend = user.getFRIENDS().iterator();
				for (int i = 0; i < user.getFRIENDS().size(); i++)
					u.friendWith(personRepository3.findByUid(friend.next().getUid()));
			}
			if (user.getTracking() != null) {
				Iterator<TwitterUser> t = user.getTracking().iterator();
				for (int i = 0; i < user.getTracking().size(); i++)
					u.Tracking(personRepository3.findByUid(t.next().getUid()));
			}
			if (user.getFollowing() != null) {
				Iterator<TwitterUser> f = user.getFollowing().iterator();
				for (int i = 0; i < user.getFollowing().size(); i++)
					u.Following(personRepository3.findByUid(f.next().getUid()));
			}
			personRepository3.save(u);
		}
		
		Iterable<TotalUser> to = personRepository3.findAll(); // 從personRepository取得之前存入Neo4j的資料
		List<String> namelist = new ArrayList<String>();
		List<String> locationlist = new ArrayList<String>();
		List<String> emaillist = new ArrayList<String>();
		List<String> birthdaylist = new ArrayList<String>();
		List<String> uidlist = new ArrayList<String>();
		List<String> genderlist = new ArrayList<String>();
		for (TotalUser user : to) {
			namelist.add(user.getName());
			locationlist.add(user.getLocation());
			emaillist.add(user.getEmail());
			birthdaylist.add(user.getBirthday());
			uidlist.add(user.getUid());
			genderlist.add(user.getGender());
		}
		String[][] rank = new Ranking2(namelist, emaillist, locationlist, birthdaylist, uidlist, genderlist)
				.getRank();
		//for(int i=0;i<rank.length;i++)
		//	System.out.println(rank[i][0]);
		int m=2,n=1,p=2;
		String[][] a = new Distinct_Tree(rank, personRepository3, m).get_Distinct_Tree();
		String[][] b = new Iterative(a, rank, personRepository3, n, m).get_Iterative();
		String[][] c = new ER(b, rank, personRepository3, p, m+n).get_ER();
		TotalUser UserNode = null, UserNode2 = null;
		for(int i=0;i<c.length;i++)
		System.out.println(c[i][0] + " / "+c[i][1]);
		for (int i = 0; i < c.length; i++) {
			if (c[i][0] == null)
				break;
			UserNode = personRepository3.findByUid(c[i][0]); // 找到第一個帳號
			UserNode2 = personRepository3.findByUid(c[i][1]); // 找到第二個帳號
			UserNode.setName(combin(UserNode.getName(), UserNode2.getName()));
			UserNode.setEmail(combin(UserNode.getEmail(), UserNode2.getEmail()));
			UserNode.setLocation(combin(UserNode.getLocation(), UserNode2.getLocation()));
			UserNode.setBirthday(combin(UserNode.getBirthday(), UserNode2.getBirthday()));
			UserNode.setUid(combin(UserNode.getUid(), UserNode2.getUid()));
			UserNode.setSocial("total");
			personRepository3.save(UserNode);
			
			if (UserNode2.getFRIENDS() != null) {
				Iterator<TotalUser> friend = UserNode2.getFRIENDS().iterator();
				for (int j = 0; j < UserNode2.getFRIENDS().size(); j++)
					UserNode.friendWith(personRepository3.findByUid(friend.next().getUid()));
			}
			if (UserNode2.getTracking() != null) {
				Iterator<TotalUser> t = UserNode2.getTracking().iterator();
				for (int j = 0; j < UserNode2.getTracking().size(); j++)
					UserNode.Tracking(personRepository3.findByUid(t.next().getUid()));
			}
			if (UserNode2.getFollowing() != null) {
				Iterator<TotalUser> f = UserNode2.getFollowing().iterator();
				for (int j = 0; j < UserNode2.getFollowing().size(); j++)
					UserNode.Following(personRepository3.findByUid(f.next().getUid()));
			}
			personRepository3.save(UserNode);
			personRepository3.delete(UserNode2);// 刪除第二帳號
		}
	}

	String combin(String a, String b) {
		if (a.equals(b))
			return a;
		String c = a + "," + b;
		return c;
	}
}
