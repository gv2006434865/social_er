package Integration;

import java.util.StringTokenizer;
import org.simmetrics.metrics.StringMetrics;
import hello.FBPersonRepository;
import hello.TWITTERPersonRepository;
import hello.TotalPersonRepository;

public class ER {

	String[][] Sdata, ans;

	public ER(String[][] data, String[][] rank, FBPersonRepository personRepository, int s, int start) {
		ans = new String[data.length * 30][3];
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
		ans = new String[data.length * 20][3];
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
		ans = new String[data.length * 20][3];
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
		System.out.println(data.length+"/" +a);
	}
	
	
	public String[][] get_ER() {
		return ans;
	}
	
	double db= 0.9;

	boolean er(String a, String b, String[][] rank, FBPersonRepository personRepository, int s, int start) {
		for (int i = 0; i < s; i++) {
			if (rank[start][0] == "name") {
				String s1 = personRepository.findByUid(a).getName();
				String s2 = personRepository.findByUid(b).getName();
				StringTokenizer st1 = new StringTokenizer(s1, ",");
				while (st1.hasMoreTokens()) {
					String t1 = st1.nextToken();
					StringTokenizer st2 = new StringTokenizer(s2, ",");
					while (st2.hasMoreTokens()) {
						String t2 = st2.nextToken();
						if(StringMetrics.jaroWinkler().compare(t1, t2) >= db)
							return true;
					}
				}
			} else if (rank[start][0] == "email") {
				String s1 = personRepository.findByUid(a).getEmail();
				s1 = new StringTokenizer(s1, "@").nextToken();
				String s2 = personRepository.findByUid(b).getEmail();
				s2 = new StringTokenizer(s2, "@").nextToken();
				StringTokenizer st1 = new StringTokenizer(s1, ",");
				while (st1.hasMoreTokens()) {
					String t1 = st1.nextToken();
					StringTokenizer st2 = new StringTokenizer(s2, ",");
					while (st2.hasMoreTokens()) {
						String t2 = st2.nextToken();
						if(StringMetrics.jaroWinkler().compare(t1, t2) >= db)
							return true;
					}
				}
			} else if (rank[start][0] == "location") {
				// ...
			} else if (rank[start][0] == "birthday") {
				String s1 = personRepository.findByUid(a).getBirthday();
				String s2 = personRepository.findByUid(b).getBirthday();
				StringTokenizer st1 = new StringTokenizer(s1, ",");
				while (st1.hasMoreTokens()) {
					String t1 = st1.nextToken();
					StringTokenizer st2 = new StringTokenizer(s2, ",");
					while (st2.hasMoreTokens()) {
						String t2 = st2.nextToken();
						if(t1.equals(t2))
							return true;
					}
				}
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
				StringTokenizer st1 = new StringTokenizer(s1, ",");
				while (st1.hasMoreTokens()) {
					String t1 = st1.nextToken();
					StringTokenizer st2 = new StringTokenizer(s2, ",");
					while (st2.hasMoreTokens()) {
						String t2 = st2.nextToken();
						if(StringMetrics.jaroWinkler().compare(t1, t2) >= db)
							return true;
					}
				}
			} else if (rank[start][0] == "email") {
				String s1 = personRepository.findByUid(a).getEmail();
				s1 = new StringTokenizer(s1, "@").nextToken();
				String s2 = personRepository.findByUid(b).getEmail();
				s2 = new StringTokenizer(s2, "@").nextToken();
				StringTokenizer st1 = new StringTokenizer(s1, ",");
				while (st1.hasMoreTokens()) {
					String t1 = st1.nextToken();
					StringTokenizer st2 = new StringTokenizer(s2, ",");
					while (st2.hasMoreTokens()) {
						
						String t2 = st2.nextToken();
						if(StringMetrics.jaroWinkler().compare(t1, t2) >= db)
							return true;
					}
				}
			} else if (rank[start][0] == "location") {
				// ...
			} else if (rank[start][0] == "birthday") {
				String s1 = personRepository.findByUid(a).getBirthday();
				String s2 = personRepository.findByUid(b).getBirthday();
				StringTokenizer st1 = new StringTokenizer(s1, ",");
				while (st1.hasMoreTokens()) {
					String t1 = st1.nextToken();
					StringTokenizer st2 = new StringTokenizer(s2, ",");
					while (st2.hasMoreTokens()) {
						String t2 = st2.nextToken();
						if(t1.equals(t2))
							return true;
					}
				}
			} else if (rank[start][0] == "uid") {
				// ...
			} else if (rank[start][0] == "gender") {
				// ...
			}
		start++;}
		return false;
	}


	boolean er(String a, String b, String[][] rank, TotalPersonRepository personRepository, int s, int start) {
		for (int i = 0; i < s; i++) {
			if (rank[start][0] == "name") {
				String s1 = personRepository.findByUid(a).getName();
				String s2 = personRepository.findByUid(b).getName();
				StringTokenizer st1 = new StringTokenizer(s1, ",");
				while (st1.hasMoreTokens()) {
					String t1 = st1.nextToken();
					StringTokenizer st2 = new StringTokenizer(s2, ",");
					while (st2.hasMoreTokens()) {
						String t2 = st2.nextToken();
						if(StringMetrics.jaroWinkler().compare(t1, t2) >= db)
							return true;
					}
				}
			} else if (rank[start][0] == "email") {
				String s1 = personRepository.findByUid(a).getEmail();
				s1 = new StringTokenizer(s1, "@").nextToken();
				String s2 = personRepository.findByUid(b).getEmail();
				s2 = new StringTokenizer(s2, "@").nextToken();
				StringTokenizer st1 = new StringTokenizer(s1, ",");
				while (st1.hasMoreTokens()) {
					String t1 = st1.nextToken();
					StringTokenizer st2 = new StringTokenizer(s2, ",");
					while (st2.hasMoreTokens()) {
						String t2 = st2.nextToken();
						if(StringMetrics.jaroWinkler().compare(t1, t2) >= db)
							return true;
					}
				}
			} else if (rank[start][0] == "location") {
				// ...
			} else if (rank[start][0] == "birthday") {
				String s1 = personRepository.findByUid(a).getBirthday();
				String s2 = personRepository.findByUid(b).getBirthday();
				StringTokenizer st1 = new StringTokenizer(s1, ",");
				while (st1.hasMoreTokens()) {
					String t1 = st1.nextToken();
					StringTokenizer st2 = new StringTokenizer(s2, ",");
					while (st2.hasMoreTokens()) {
						String t2 = st2.nextToken();
						if(t1.equals(t2))
							return true;
					}
				}
			} else if (rank[start][0] == "uid") {
				// ...
			} else if (rank[start][0] == "gender") {
				// ...
			}
		start++;}
		return false;
	}

}
