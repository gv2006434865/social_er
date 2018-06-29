package Integration;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.commons.lang3.ArrayUtils;
import org.simmetrics.metrics.StringMetrics;

import com.google.common.collect.Lists;

import hello.FBPersonRepository;
import hello.FbUser;
import hello.TWITTERPersonRepository;
import hello.TotalPersonRepository;
import hello.TwitterUser;

public class ER {

	String[][] Sdata, ans;

	public ER(String[][] data, String[][] rank, FBPersonRepository personRepository, int s, int start) {
		ans = new String[data.length * 2][3];
		int a = 0;
		Sdata = data;
		
		for (int j = 0; j < Sdata.length; j++) {
			if (Sdata[j][0] == null)
				break;
			for (int o = 0; o < Sdata[j].length - 1; o++) { // 組內值
				if (Sdata[j][o] == null)
					break;
				for (int p = o + 1; p < Sdata[j].length; p++) {
					if (Sdata[j][p] == null)
						break;
					if (er(Sdata[j][o], Sdata[j][p], rank, personRepository, s, start)) {
						ans[a][0] = Sdata[j][o];
						ans[a][1] = Sdata[j][p];
						a++;
					}
				}
			}
		}
	}
	
	public ER(String[][] data, String[][] rank, TWITTERPersonRepository personRepository, int s, int start) {
		ans = new String[data.length * 2][3];
		int a = 0;
		Sdata = data;
		
		for (int j = 0; j < Sdata.length; j++) {
			if (Sdata[j][0] == null)
				break;
			for (int o = 0; o < Sdata[j].length - 1; o++) { // 組內值
				if (Sdata[j][o] == null)
					break;
				for (int p = o + 1; p < Sdata[j].length; p++) {
					if (Sdata[j][p] == null)
						break;
					if (er(Sdata[j][o], Sdata[j][p], rank, personRepository, s, start)) {
						ans[a][0] = Sdata[j][o];
						ans[a][1] = Sdata[j][p];
						a++;
					}
				}
			}
		}
	}
	
	public ER(String[][] data, String[][] rank, TotalPersonRepository personRepository, int s, int start) {
		ans = new String[data.length * 10][3];
		int a = 0;
		Sdata = data;
		
		for (int j = 0; j < Sdata.length; j++) {
			if (Sdata[j][0] == null)
				break;
			for (int o = 0; o < Sdata[j].length - 1; o++) { // 組內值
				if (Sdata[j][o] == null)
					break;
				for (int p = o + 1; p < Sdata[j].length; p++) {
					if (Sdata[j][p] == null)
						break;
					System.out.println("--------"+Sdata[j][o]+"/"+ Sdata[j][p]);
					if (er(Sdata[j][o], Sdata[j][p], rank, personRepository, s, start)) {
						ans[a][0] = Sdata[j][o];
						ans[a][1] = Sdata[j][p];
						a++;
					}
				}
			}
		}
		System.out.println(data.length+"/" +a);
	}
	
	
	public String[][] get_ER() {
		return ans;
	}
	

	boolean er(String a, String b, String[][] rank, FBPersonRepository personRepository, int s, int start) {
		for (int i = 0; i < s; i++) {
			if (rank[start][0] == "name") {
				String s1 = personRepository.findByUid(a).getName();
				String s2 = personRepository.findByUid(b).getName();
				System.out.println("name : "+s1+"/"+s2+ " : "+StringMetrics.jaroWinkler().compare(s1, s2));
				if(StringMetrics.jaroWinkler().compare(s1, s2) > 0.8)
					return true;
			} else if (rank[start][0] == "email") {
				String s1 = personRepository.findByUid(a).getEmail();
				s1 = new StringTokenizer(s1, "@").nextToken();
				String s2 = personRepository.findByUid(b).getEmail();
				s2 = new StringTokenizer(s2, "@").nextToken();
				System.out.println("email : "+s1+"/"+s2+ " : "+StringMetrics.jaroWinkler().compare(s1, s2));
				if(StringMetrics.jaroWinkler().compare(s1, s2) > 0.8)
					return true;
			} else if (rank[start][0] == "location") {
				// ...
			} else if (rank[start][0] == "birthday") {
				String s1 = personRepository.findByUid(a).getBirthday();
				String s2 = personRepository.findByUid(b).getBirthday();
				System.out.println("birthday : "+s1+"/"+s2);
				if(s1.equals(s2))
					return true;
			} else if (rank[start][0] == "uid") {
				// ...
			} else if (rank[start][0] == "gender") {
				// ...
			}
		start++;}
		return false;
	}


	boolean er(String a, String b, String[][] rank, TWITTERPersonRepository personRepository, int s, int start) {
		for (int i = 0; i < s; i++) {
			if (rank[start][0] == "name") {
				String s1 = personRepository.findByUid(a).getName();
				String s2 = personRepository.findByUid(b).getName();
				System.out.println("name : "+s1+"/"+s2+ " : "+StringMetrics.jaroWinkler().compare(s1, s2));
				if(StringMetrics.jaroWinkler().compare(s1, s2) > 0.8)
					return true;
			} else if (rank[start][0] == "email") {
				String s1 = personRepository.findByUid(a).getEmail();
				s1 = new StringTokenizer(s1, "@").nextToken();
				String s2 = personRepository.findByUid(b).getEmail();
				s2 = new StringTokenizer(s2, "@").nextToken();
				System.out.println("email : "+s1+"/"+s2+ " : "+StringMetrics.jaroWinkler().compare(s1, s2));
				if(StringMetrics.jaroWinkler().compare(s1, s2) > 0.8)
					return true;
			} else if (rank[start][0] == "location") {
				// ...
			} else if (rank[start][0] == "birthday") {
				String s1 = personRepository.findByUid(a).getBirthday();
				String s2 = personRepository.findByUid(b).getBirthday();
				System.out.println("birthday : "+s1+"/"+s2);
				if(s1.equals(s2))
					return true;
			} else if (rank[start][0] == "uid") {
				// ...
			} else if (rank[start][0] == "gender") {
				// ...
			}
		start++;}
		return false;
	}


	boolean er(String a, String b, String[][] rank, TotalPersonRepository personRepository, int s, int start) {
		System.out.println("er : " + a +"_" + b + " / " + s + " / " + start);
		for (int i = 0; i < s; i++) {
			if (rank[start][0] == "name") {
				String s1 = personRepository.findByUid(a).getName();
				String s2 = personRepository.findByUid(b).getName();
				System.out.println("name : "+s1+"/"+s2+ " : "+StringMetrics.jaroWinkler().compare(s1, s2));
				if(StringMetrics.jaroWinkler().compare(s1, s2) > 0.8)
					return true;
			} else if (rank[start][0] == "email") {
				String s1 = personRepository.findByUid(a).getEmail();
				s1 = new StringTokenizer(s1, "@").nextToken();
				String s2 = personRepository.findByUid(b).getEmail();
				s2 = new StringTokenizer(s2, "@").nextToken();
				System.out.println("email : "+s1+"/"+s2+ " : "+StringMetrics.jaroWinkler().compare(s1, s2));
				if(StringMetrics.jaroWinkler().compare(s1, s2) > 0.8)
					return true;
			} else if (rank[start][0] == "location") {
				// ...
			} else if (rank[start][0] == "birthday") {
				String s1 = personRepository.findByUid(a).getBirthday();
				String s2 = personRepository.findByUid(b).getBirthday();
				System.out.println("birthday : "+s1+"/"+s2);
				if(s1.equals(s2))
					return true;
			} else if (rank[start][0] == "uid") {
				// ...
			} else if (rank[start][0] == "gender") {
				// ...
			}
		start++;}
		return false;
	}

}
