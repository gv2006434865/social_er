package tree;

import java.math.BigDecimal;
import java.util.List;
import java.util.StringTokenizer;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;

public class Ranking {
	String[][] st;
	Tree tr_name, tr_location, tr_email, tr_birthday, tr_uid;
	String sortlist[][] = new String[5][5];
	String[][] list_Name, list_Location, list_Email, list_Birthday, list_Uid;

	public Ranking(List<String> ls_name, List<String> ls_email, List<String> ls_location, List<String> ls_birthday, List<String> ls_Uid) {
		this.list_Name = list_to_arr(ls_name);
		this.list_Email = list_to_arr_for_email(ls_email);
		this.list_Location = list_to_arr2(ls_location);
		this.list_Birthday = list_to_arr2(ls_birthday);
		//System.out.println(ls_birthday);
		
		this.tr_name = tree_built(list_Name);
		this.tr_location = tree_built(list_Location);
		this.tr_email = tree_built(list_Email);
		this.tr_birthday = tree_built(list_Birthday);
		sortlist[0][0] = "name";
		sortlist[0][1] = Double.toString(tr_name.getPoint());
		sortlist[0][2] = "0";
		sortlist[1][0] = "email";
		sortlist[1][1] = Double.toString(tr_email.getPoint());
		sortlist[1][2] = "1";
		sortlist[2][0] = "location";
		sortlist[2][1] = Double.toString(tr_location.getPoint());
		sortlist[2][2] = "2";
		sortlist[3][0] = "birthday";
		sortlist[3][1] = Double.toString(tr_birthday.getPoint());
		sortlist[3][2] = "3";
		sortlist[4][0] = "uid";
		sortlist[4][1] = "0";
		sortlist[4][2] = "4";
		//System.out.println("tr_name.getPoint() :"+tr_name.getPoint());
		Ranking(); //取得屬性計算順序
	}

	static String[][] data, recount;
	static float threshold, bottom;
	int run, run_time, len, success, fail;
	float Coverage;

	public String[][] getRanking() {
		return this.sortlist;
	}
	
	String[][] Ranking() {
		int l = sortlist.length;
		for (int i = 0; i < l-1; i++) {
			for (int j = 0; j < l-1-i; j++) {
				if(Double.parseDouble(sortlist[j][1])<Double.parseDouble(sortlist[j+1][1])) {
					final String[] buffer =  sortlist[j+1];
					sortlist[j+1] = sortlist[j];
					sortlist[j] = buffer;
				}
			}
		}
		return sortlist;
	}

	String[][] list_to_arr(List<String> ls) { //資料分組，略過空值
		String[][] data = new String[ls.size() * (ls.size() - 1) / 2][5];
		StringMetric jaro = StringMetrics.jaroWinkler();
		float result = 0;
		int n = 0;
		for (int i = 0; i < ls.size() - 1; i++) {
			for (int j = i + 1; j < ls.size(); j++) {
				String d1 = ls.get(i);
				String d2 = ls.get(j);
				if (ls.get(i) == null || ls.get(j) == null)
					continue;
				if (i != j) {
					data[n][0] = d1;
					data[n][1] = d2;
					result = jaro.compare(d1, d2);
					data[n][2] = Float.toString(result);
				}
				n++;
			}
		}
		String[][] ans = new String[n][5];
		for (int i = 0; i < data.length; i++) {
			if (data[i][0] == null)
				break;
			ans[i] = data[i];
		}
		return ans;
	}
	
	String[][] list_to_arr2(List<String> ls) { //資料分組，略過空值
		String[][] data = new String[ls.size() * (ls.size() - 1) / 2][5];
		float result = 0;
		int n = 0;
		for (int i = 0; i < ls.size() - 1; i++) {
			for (int j = i + 1; j < ls.size(); j++) {
				String d1 = ls.get(i);
				String d2 = ls.get(j);
				if (ls.get(i) == null || ls.get(j) == null)
					continue;
				if (i != j) {
					data[n][0] = d1;
					data[n][1] = d2;
					result = StringMetrics.levenshtein().compare(d1, d2);
					data[n][2] = Float.toString(result);
				}
				n++;
			}
		}
		String[][] ans = new String[n][5];
		for (int i = 0; i < data.length; i++) {
			if (data[i][0] == null)
				break;
			ans[i] = data[i];
		}
		return ans;
	}
	
	
	String[][] list_to_arr_for_email(List<String> ls) { //資料分組，略過空值
		String[][] data= new String[ls.size() * (ls.size() - 1) / 2][5];
		int n = 0;
		for (int i = 0; i < ls.size() - 1; i++) {
			i++;
			for (int j = i + 1; j < ls.size(); j++) {
				String d1 = ls.get(i);
				String d2 = ls.get(j);
				if (ls.get(i) == null || ls.get(j) == null)
					continue;
				if (i != j) {
					data[n][0] = d1;
					data[n][1] = d2;
					email_compare(d1, d2);
					data[n][2] = Float.toString(email_compare(d1, d2));
				}
				n++;
			}
		}
		String[][] ans = new String[n][5];
		for (int i = 0; i < data.length; i++) {
			if (data[i][0] == null)
				break;
			ans[i] = data[i];
		}
		return ans;
	}
	
	
	float email_compare(String d1, String d2) {
		float birth = 0;
		String[] d1a =  new String[3],d2a =  new String[3];
		StringTokenizer st1 = new StringTokenizer(d1, "@");
		StringTokenizer st2 = new StringTokenizer(d2, "@");
		int q=0,p=0;
		while (st1.hasMoreTokens()){
			d1a[q] = st1.nextToken();
		    q++;
		}
		while (st2.hasMoreTokens()){
			d2a[p] = st2.nextToken();
		    p++;
		}
		StringMetric jaro = StringMetrics.jaroWinkler();
		float r0 = jaro.compare(d1a[0], d2a[0]);
		if(r0>0.8) 
			birth = 1;
		return birth;
	}
	
	Tree tree_built(String[][] data_array) {
		data = data_array;
		StringMetric jaro = StringMetrics.jaroWinkler();
		int n = 0, m = 0;
		for (int i = 0; i < data.length; i++) {
			float sim = jaro.compare(data[i][0], data[i][1]);
			if (Float.parseFloat(data_array[i][2]) >= 0.55) { 
				n++;
			} else {
				m++;
			}
		}
		return new Tree(n, m);
	}

	public float rounding(float a) {
		float ft = a;
		int scale = 2;
		int roundingMode = 4;
		BigDecimal bd = new BigDecimal((double) ft);
		bd = bd.setScale(scale, roundingMode);
		ft = bd.floatValue();
		return ft;
	}
}
