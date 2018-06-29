package tree;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;

public class Ranking2 {
	String[][] ans;

	public Ranking2(List<String> ls_name, List<String> ls_email, List<String> ls_location, List<String> ls_birthday,
			List<String> ls_Uid, List<String> ls_gender) {
		ans = Ranking(Best_attr(ls_name), Best_attr(ls_email), Best_attr(ls_location), Best_attr(ls_birthday),
				Best_attr(ls_Uid),Best_attr(ls_gender));
	}
	
	public String[][] getRank() {
		return ans;
	};

	Set Best_attr(List<String> ls) { //取得不同值
		Set<String> Set1 = new HashSet<String>();
		for (int i = 0; i < ls.size(); i++) {
			if(ls.get(i) != "")
			Set1.add(ls.get(i));
		}
		return Set1;
	}
	//屬性排序
	String[][] Ranking(Set S_name, Set S_email, Set S_location, Set S_birthday, Set S_uid, Set S_gender) {
		String[][] rank = new String[6][2];
		rank[0][0] = "name";
		rank[0][1] = Integer.toString(S_name.size());		
		rank[1][0] = "email";
		rank[1][1] = Integer.toString(S_email.size());
		rank[2][0] = "location";
		rank[2][1] = Integer.toString(S_location.size());
		rank[3][0] = "birthday";
		rank[3][1] = Integer.toString(S_birthday.size());
		rank[4][0] = "uid";
		rank[4][1] = Integer.toString(S_uid.size());
		rank[5][0] = "gender";
		rank[5][1] = Integer.toString(S_gender.size());		
		for (int i = 0; i < rank.length - 1; i++) {
			for (int j = 0; j < rank.length - 1; j++) {
				if (Integer.valueOf(rank[j + 1][1]) < Integer.valueOf(rank[j][1])) {
					String[] tump = rank[j];
					rank[j] = rank[j + 1];
					rank[j + 1] = tump;
				}
			}
		}
		return rank;
	}

}
