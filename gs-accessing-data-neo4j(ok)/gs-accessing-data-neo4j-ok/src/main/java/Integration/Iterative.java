package Integration;

import java.util.StringTokenizer;
import org.simmetrics.metrics.StringMetrics;
import hello.FBPersonRepository;
import hello.TWITTERPersonRepository;
import hello.TotalPersonRepository;

public class Iterative {

	String[][] Sdata;

	public Iterative(String[][] data, String[][] rank, FBPersonRepository personRepository, int s, int start) {
		Sdata = data;
		for (int i = start; i <= s; i++) {
			if (rank[i][0] == "name") {
				// 反覆分組
				for (int j = 0; j < Sdata.length - 1; j++) {// 取第一組的值，分別與後一組每個值做比較
					for (int o = 0; o < Sdata[j].length; o++) { // 組內值
						if (Sdata[j][o] == null)
							break;
						String s1 = personRepository.findByUid(Sdata[j][o]).getName();
						for (int k = j + 1; k < Sdata.length; k++) // 比較組
							for (int p = 0; p < Sdata[k].length; p++) { // 組內值
								if (Sdata[k][p] == null)
									break;
								String s2 = personRepository.findByUid(Sdata[k][p]).getName();
								if (StringMetrics.jaroWinkler().compare(s1, s2) > 0.8) {
									int b =0;
									for(int a=0;a<Sdata[j].length;a++) {
										if(Sdata[j][a] ==null) {
											if(Sdata[k][b]==null) break;
											Sdata[j][a] = Sdata[k][b];
											b++;
										}
									}
									Sdata = remove(Sdata, k);
									k--;
									break;
								}
							}
					}
				}
			} else if (rank[i][0] == "email") {
				for (int j = 0; j < Sdata.length - 1; j++) {// 取第一組的值，分別與後一組每個值做比較
					for (int o = 0; o < Sdata[j].length; o++) { // 組內值
						if (Sdata[j][o] == null)
							break;
						String s1 = personRepository.findByUid(Sdata[j][o]).getEmail();
						s1 = new StringTokenizer(s1, "@").nextToken();
						System.out.println("data[" + j + "][" + o + "]" + Sdata[j][o] + "/" + s1);
						for (int k = j + 1; k < Sdata.length; k++) // 比較組
							for (int p = 0; p < Sdata[k].length; p++) { // 組內值
								if (Sdata[k][p] == null)
									break;
								String s2 = personRepository.findByUid(Sdata[k][p]).getEmail();
								s2 = new StringTokenizer(s2, "@").nextToken();
								if (StringMetrics.jaroWinkler().compare(s1, s2) > 0.8) {
									System.out.println("data2[" + k + "][" + p + "]" + Sdata[k][p] + "/" + s2 + "/"
											+ StringMetrics.jaroWinkler().compare(s1, s2));
									int b =0;
									for(int a=0;a<Sdata[j].length;a++) {
										if(Sdata[j][a] ==null) {
											if(Sdata[k][b]==null) break;
											Sdata[j][a] = Sdata[k][b];
											b++;
										}
									}
									Sdata = remove(Sdata, k);
									k--;
									break;
								}
							}
					}
				}
			} else if (rank[i][0] == "location") {
				// ...
			} else if (rank[i][0] == "birthday") {
				for (int j = 0; j < Sdata.length - 1; j++) {// 取第一組的值，分別與後一組每個值做比較
					for (int o = 0; o < Sdata[j].length; o++) { // 組內值
						if (Sdata[j][o] == null)
							break;
						String s1 = personRepository.findByUid(Sdata[j][o]).getBirthday();
						for (int k = j + 1; k < Sdata.length; k++) // 比較組
							for (int p = 0; p < Sdata[k].length; p++) { // 組內值
								if (Sdata[k][p] == null)
									break;
								String s2 = personRepository.findByUid(Sdata[k][p]).getBirthday();
								if (StringMetrics.levenshtein().compare(s1, s2) > 0.8) {
									int b =0;
									for(int a=0;a<Sdata[j].length;a++) {
										if(Sdata[j][a] ==null) {
											if(Sdata[k][b]==null) break;
											Sdata[j][a] = Sdata[k][b];
											b++;
										}
									}
									Sdata = remove(Sdata, k);
									k--;
									break;
								}
							}
					}
				}
			} else if (rank[i][0] == "uid") {
				// ...
			} else if (rank[i][0] == "gender") {
				// ...
			}
		}
	}

	public Iterative(String[][] data, String[][] rank, TWITTERPersonRepository personRepository, int s, int start) {
		Sdata = data;
		for (int i = start; i <= s; i++) {
			if (rank[i][0] == "name") {
				// 反覆分組
				for (int j = 0; j < Sdata.length - 1; j++) {// 取第一組的值，分別與後一組每個值做比較
					for (int o = 0; o < Sdata[j].length; o++) { // 組內值
						if (Sdata[j][o] == null)
							break;
						String s1 = personRepository.findByUid(Sdata[j][o]).getName();
						for (int k = j + 1; k < Sdata.length; k++) // 比較組
							for (int p = 0; p < Sdata[k].length; p++) { // 組內值
								if (Sdata[k][p] == null)
									break;
								String s2 = personRepository.findByUid(Sdata[k][p]).getName();
								if (StringMetrics.jaroWinkler().compare(s1, s2) > 0.8) {
									int b =0;
									for(int a=0;a<Sdata[j].length;a++) {
										if(Sdata[j][a] ==null) {
											if(Sdata[k][b]==null) break;
											Sdata[j][a] = Sdata[k][b];
											b++;
										}
									}
									Sdata = remove(Sdata, k);
									k--;
									break;
								}
							}
					}
				}
			} else if (rank[i][0] == "email") {
				for (int j = 0; j < Sdata.length - 1; j++) {// 取第一組的值，分別與後一組每個值做比較
					for (int o = 0; o < Sdata[j].length; o++) { // 組內值
						if (Sdata[j][o] == null)
							break;
						String s1 = personRepository.findByUid(Sdata[j][o]).getEmail();
						s1 = new StringTokenizer(s1, "@").nextToken();
						System.out.println("data[" + j + "][" + o + "]" + Sdata[j][o] + "/" + s1);
						for (int k = j + 1; k < Sdata.length; k++) // 比較組
							for (int p = 0; p < Sdata[k].length; p++) { // 組內值
								if (Sdata[k][p] == null)
									break;
								String s2 = personRepository.findByUid(Sdata[k][p]).getEmail();
								s2 = new StringTokenizer(s2, "@").nextToken();
								if (StringMetrics.jaroWinkler().compare(s1, s2) > 0.8) {
									System.out.println("data2[" + k + "][" + p + "]" + Sdata[k][p] + "/" + s2 + "/"
											+ StringMetrics.jaroWinkler().compare(s1, s2));
									int b =0;
									for(int a=0;a<Sdata[j].length;a++) {
										if(Sdata[j][a] ==null) {
											if(Sdata[k][b]==null) break;
											Sdata[j][a] = Sdata[k][b];
											b++;
										}
									}
									Sdata = remove(Sdata, k);
									k--;
									break;
								}
							}
					}
				}
			} else if (rank[i][0] == "location") {
				// ...
			} else if (rank[i][0] == "birthday") {
				for (int j = 0; j < Sdata.length - 1; j++) {// 取第一組的值，分別與後一組每個值做比較
					for (int o = 0; o < Sdata[j].length; o++) { // 組內值
						if (Sdata[j][o] == null)
							break;
						String s1 = personRepository.findByUid(Sdata[j][o]).getBirthday();
						for (int k = j + 1; k < Sdata.length; k++) // 比較組
							for (int p = 0; p < Sdata[k].length; p++) { // 組內值
								if (Sdata[k][p] == null)
									break;
								String s2 = personRepository.findByUid(Sdata[k][p]).getBirthday();
								if (StringMetrics.levenshtein().compare(s1, s2) > 0.8) {
									int b =0;
									for(int a=0;a<Sdata[j].length;a++) {
										if(Sdata[j][a] ==null) {
											if(Sdata[k][b]==null) break;
											Sdata[j][a] = Sdata[k][b];
											b++;
										}
									}
									Sdata = remove(Sdata, k);
									k--;
									break;
								}
							}
					}
				}
			} else if (rank[i][0] == "uid") {
				// ...
			} else if (rank[i][0] == "gender") {
				// ...
			}
		}
	}


	public Iterative(String[][] data, String[][] rank, TotalPersonRepository personRepository, int s, int start) {
		Sdata = data;
		for (int i = start; i <= s; i++) {
			if (rank[i][0] == "name") {
				// 反覆分組
				for (int j = 0; j < Sdata.length - 1; j++) {// 取第一組的值，分別與後一組每個值做比較
					for (int o = 0; o < Sdata[j].length; o++) { // 組內值
						if (Sdata[j][o] == null)
							break;
						String s1 = personRepository.findByUid(Sdata[j][o]).getName();
						for (int k = j + 1; k < Sdata.length; k++) // 比較組
							for (int p = 0; p < Sdata[k].length; p++) { // 組內值
								if (Sdata[k][p] == null)
									break;
								String s2 = personRepository.findByUid(Sdata[k][p]).getName();
								if (StringMetrics.jaroWinkler().compare(s1, s2) > 0.8) {
									int b =0;
									for(int a=0;a<Sdata[j].length;a++) {
										if(Sdata[j][a] ==null) {
											if(Sdata[k][b]==null) break;
											Sdata[j][a] = Sdata[k][b];
											b++;
										}
									}
									Sdata = remove(Sdata, k);
									k--;
									break;
								}
							}
					}
				}
			} else if (rank[i][0] == "email") {
				for (int j = 0; j < Sdata.length - 1; j++) {// 取第一組的值，分別與後一組每個值做比較
					for (int o = 0; o < Sdata[j].length; o++) { // 組內值
						if (Sdata[j][o] == null)
							break;
						String s1 = personRepository.findByUid(Sdata[j][o]).getEmail();
						s1 = new StringTokenizer(s1, "@").nextToken();
						System.out.println("data[" + j + "][" + o + "]" + Sdata[j][o] + "/" + s1);
						for (int k = j + 1; k < Sdata.length; k++) // 比較組
							for (int p = 0; p < Sdata[k].length; p++) { // 組內值
								if (Sdata[k][p] == null)
									break;
								String s2 = personRepository.findByUid(Sdata[k][p]).getEmail();
								s2 = new StringTokenizer(s2, "@").nextToken();
								if (StringMetrics.jaroWinkler().compare(s1, s2) > 0.8) {
									System.out.println("data2[" + k + "][" + p + "]" + Sdata[k][p] + "/" + s2 + "/"
											+ StringMetrics.jaroWinkler().compare(s1, s2));
									int b =0;
									for(int a=0;a<Sdata[j].length;a++) {
										if(Sdata[j][a] ==null) {
											if(Sdata[k][b]==null) break;
											Sdata[j][a] = Sdata[k][b];
											b++;
										}
									}
									Sdata = remove(Sdata, k);
									k--;
									break;
								}
							}
					}
				}
			} else if (rank[i][0] == "location") {
				// ...
			} else if (rank[i][0] == "birthday") {
				for (int j = 0; j < Sdata.length - 1; j++) {// 取第一組的值，分別與後一組每個值做比較
					for (int o = 0; o < Sdata[j].length; o++) { // 組內值
						if (Sdata[j][o] == null)
							break;
						String s1 = personRepository.findByUid(Sdata[j][o]).getBirthday();
						for (int k = j + 1; k < Sdata.length; k++) // 比較組
							for (int p = 0; p < Sdata[k].length; p++) { // 組內值
								if (Sdata[k][p] == null)
									break;
								String s2 = personRepository.findByUid(Sdata[k][p]).getBirthday();
								if (StringMetrics.levenshtein().compare(s1, s2) > 0.8) {
									int b =0;
									for(int a=0;a<Sdata[j].length;a++) {
										if(Sdata[j][a] ==null) {
											if(Sdata[k][b]==null) break;
											Sdata[j][a] = Sdata[k][b];
											b++;
										}
									}
									Sdata = remove(Sdata, k);
									k--;
									break;
								}
							}
					}
				}
			} else if (rank[i][0] == "uid") {
				// ...
			} else if (rank[i][0] == "gender") {
				// ...
			}
		}
	}

	
	public String[][] get_Iterative(){
		return Sdata;
	}
	
	static String[][] remove(String[][] a, int n) {
		String[][] b = new String[a.length - 1][];
		int ar = 0;
		for (int i = 0; i < a.length; i++) {
			if (i == n)
				continue;
			b[ar] = a[i];
			ar++;
		}
		return b;
	}
}
