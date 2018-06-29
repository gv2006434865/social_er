
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;

public class test {

	static float threshold;

	public static void main(String[] args) throws Exception {

		String str1 = "Taichung";
		String str2 = "Tainan";
		StringMetric jaro = StringMetrics.jaroWinkler();
		StringMetric leve = StringMetrics.levenshtein();
		float result1 = StringMetrics.jaroWinkler().compare(str1, str2);
		float result2 = StringMetrics.levenshtein().compare(str1, str2);
		
		System.out.println(result1); // 0.8055556
		System.out.println(result2); // 0.5
		System.out.println("--------"); // 0.5
		System.out.println(StringMetrics.jaroWinkler()
				.compare("gv2017@ yahoo.com.tw", "style44@yahoo.com.tw"));
		System.out.println("--------"); // 0.5
		System.out.println(str1+str2); // 0.5
		System.out.println("--------"); // 0.5

		
		/*String[] userinfo = JsonfileToString("twitter_follower.json");
		for (int i = 0; i < userinfo.length; i++) {
			JSONObject user = (JSONObject) new JSONParser().parse(userinfo[i]);
			System.out.println(user.get("Email"));
		}

		for (int i = 0; i < userinfo.length; i++) {
		try {
			JSONObject user = (JSONObject) new JSONParser().parse(userinfo[i]);
			System.out.println(user.get("Uname"));
		} catch (Exception e) {
		   continue;   // go to the top of the for loop
		}
		}
		
		String[] to = JsonfileToString("twitter_follower.json");
		String[] to2 = JsonfileToString("fb_userinfo.json");

		JSONObject json = (JSONObject) new JSONParser().parse(to[6]);
		System.out.println(json.get("Uname"));

		JSONObject json2 = (JSONObject) new JSONParser().parse(to2[0]);
		System.out.println(json2.get("Uname"));*/
		

		Set<String> nameSet = new HashSet<String>();
		//While(Repository.findAll().iterator().hasNext()) 
		//nameSet.add(Repository.findAll().iterator().next().getName());
		//System.out.print(nameSet);
		nameSet.add("陳芊安");  
		nameSet.add("陳芊安");  
		nameSet.add("裡木棉");  
		System.out.println(nameSet);  
		System.out.println(nameSet.size());
		nameSet.remove("裡木棉");
		System.out.print(nameSet);
        String d1 = "陳芊安,陳芊安,李木棉";
        
        String[] a= {"hello","world","java"};
        String[] b= {"1","2"};
        String[] aAndb = ArrayUtils.addAll(a, b);
        
        for (int i=0;i<aAndb.length;i++) {
        	   System.out.println(aAndb[i]);
        	}
        
        String[][] c= {{"1","2"},{"3"},{"8","9"}};
        String[][] d= remove(c, 1);
        for(int i=0;i<d.length;i++)
        	for(int j=0;j < d[i].length;j++)
            		System.out.println(d[i][j]);
      /*  StringTokenizer st1 = new StringTokenizer(d1, ",");
        int i=1;
        while (st1.hasMoreTokens())
        	nameSet.add(st1.nextToken());
		
        // 输出结果 张三  李四  王五
 
        for(String name : nameSet){  
        	if(i!=nameSet.size())System.out.print(name + ","); 
        	else
            System.out.print(name);  
        	i++;
        }  
        
        String test  = ("chaojimali");
        test = test.replace("chaoji","");
        System.out.print(test);  */

	}

	static String[][] remove(String[][] a, int n) {
		String[][] b = new String[a.length-1][];
		int ar=0;
		for (int i = 0; i < a.length; i++) {
			if (i == n)
				continue;
			b[ar] = a[i];
			ar++;
		}
		return b;
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
