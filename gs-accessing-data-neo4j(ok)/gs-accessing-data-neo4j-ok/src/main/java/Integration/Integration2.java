package Integration;

import java.math.BigDecimal;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;

public class Integration2 {
	String[][] data, rank, Name_Array, Email_Array, Location_Array, Birthday_Array, Uid_Array;
	TD_Array td;
	float[][] combine2;

	public Integration2(String[][] data, String[][] rank) {
		this.data = data;
		this.rank = rank;
		System.out.println("data.length : "+data.length);
		float[][][] combine = new float[5][data.length][data.length];
		String[][][] srtcom = new String[5][data.length][data.length];
		for (int i = 0; i < rank.length; i++) {
			//float Weights = (float) ((4 - i) * 0.1);
			float Weights=0.1f;
			if(i==0) Weights=0.3f; 
			else if(i==1) Weights=0.3f;
			else if(i==2) Weights=0.2f; 
			else if(i==3) Weights=0.2f; 
			else if(i==4) Weights=0.1f; //五種屬性，依照排序的權重為 (5-i)*2/3
			td = Similarity(Integer.valueOf(rank[i][2]), Weights);
			for (int j = 0; j < data.length - 1; j++) { // 辨識成功的紀錄組能取得權重值
				for (int k = 0; k < td.three[j]; k++) {
					combine[i][j][k] = td.TD[j][k]; // i : 屬性 / j & k : 資料匹配組
					srtcom[i] = td.TD_Str;
				}
			}
		}
		combine2 = new float[data.length][data.length];
		for (int j = 0; j < data.length - 1; j++) { // 權重加總，超過0.8的就是同一個體
			for (int k = 0; k < td.three[j]; k++) {
				float a = combine[0][j][k];
				float b = combine[1][j][k];
				float c = combine[2][j][k];
				float d = combine[3][j][k];
				float e = combine[4][j][k];
				this.combine2[j][k] = rounding(a + b + c + d + e);
			}
		}

		String[][] getCombineName = new String[data.length][data.length];
		String[][] getCombineEmail = new String[data.length][data.length];
		String[][] getCombineLocation = new String[data.length][data.length];
		String[][] getCombinebirthday = new String[data.length][data.length];
		String[][] getCombineUid = new String[data.length][data.length];
		for (int i = 0; i < rank.length; i++) { // 取得各組紀錄內容字串(合併過)
			switch (rank[i][2]) {
			case "0":
				getCombineName = srtcom[i];
				Name_Array = getCombineName;
				break;
			case "1":
				getCombineEmail = srtcom[i];
				Email_Array = getCombineEmail;
				break;
			case "2":
				getCombineLocation = srtcom[i];
				Location_Array = getCombineLocation;
				break;
			case "3":
				getCombinebirthday = srtcom[i];
				Birthday_Array = getCombinebirthday;
				break;
			case "4":
				getCombineUid = srtcom[i];
				Uid_Array = getCombineUid;
				break;
			}
		}
	}


	TD_Array Similarity(int i, float Weights) { // 按照屬性排序取得數值和組合字串
		float[][] sim = new float[data.length][data.length];
		String[][] str = new String[data.length][data.length];
		StringMetric jaro = StringMetrics.jaroWinkler();
		int n = 0;
		int[] p = new int[data.length];
		
		for (int o = 0; o < data.length - 1; o++) {
			int m = 0;
			
			for (int j = o + 1; j < data.length; j++) {
				if (data[o][i] == null || data[j][i] == null) {
					m++;
					sim[n][m] = (float) 0.0;
					if (data[o][i] == null) {
						str[n][m] = data[j][i];
					} else {
						str[n][m] = data[o][i];
					}
					continue;
				}
				
				String d1 = data[o][i] + "," + data[j][i], d2 = null;
				Float threshold = 0.0f;
				
				if(i == 0) { //Name
					threshold = jaro.compare(data[o][i], data[j][i]);
				}else if(i ==1) { //Email
					StringTokenizer st1 = new StringTokenizer(data[o][i], "@");
					StringTokenizer st2 = new StringTokenizer(data[j][i], "@");
					threshold = jaro.compare(st1.nextToken(), st2.nextToken());
					//if(threshold>0.8)System.out.println(data[o][i]+"/"+ data[j][i]);
				}else if(i ==2) { //Location
					threshold = StringMetrics.levenshtein().compare(data[o][i], data[j][i]);
				}else if(i ==3) {//birthday
					threshold = StringMetrics.levenshtein().compare(data[o][i], data[j][i]);
				}else if(i==4) {//Uid
					threshold = StringMetrics.levenshtein().compare(data[o][i], data[j][i]);
				}
				Set<String> nameSet = new HashSet<String>();  
				if (threshold >= 0.8) { //設定合併的閾值
					sim[n][m] = Weights;
					//System.out.println(d1);
					StringTokenizer st1 = new StringTokenizer(d1, ",");
			        while (st1.hasMoreTokens())
			        	nameSet.add(st1.nextToken());
			        int d=1;
			        for(String name : nameSet){  
			        	//System.out.println(name);
			        	if(d!=nameSet.size()) d2 += name + ","; 
			        	else
			        	d2 +=name;  
			        	d++;
			        } 
			       // System.out.println("d2 : "+d2.replace("null",""));
			        str[n][m] =d2.replace("null","");
				} else {
					str[n][m] = data[o][i] + "," + data[j][i];
				}
				m++;
			}
			p[o] = m;
			n++;
		}
		return new TD_Array(sim, str, p);
	}

	float rounding(float a) { // 整理float成小數點下兩位
		float ft = a;
		int scale = 2;
		int roundingMode = 4;
		BigDecimal bd = new BigDecimal((double) ft);
		bd = bd.setScale(scale, roundingMode);
		ft = bd.floatValue();
		return ft;
	}	

	public float[][] getCombineScore() {
		return combine2;
	}

	public String[][] getCombineName() {
		return Name_Array;
	}

	public String[][] getCombineEmail() {
		return Email_Array;
	}

	public String[][] getCombineLocation() {
		return Location_Array;
	}

	public String[][] getCombineBirthday() {
		return Birthday_Array;
	}
	
	public String[][] getCombineUid() {
		return Uid_Array;
	}
}
