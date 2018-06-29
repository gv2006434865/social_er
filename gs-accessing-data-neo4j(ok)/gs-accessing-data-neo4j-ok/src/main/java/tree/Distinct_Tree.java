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

import hello.FBPersonRepository;
import hello.FbUser;
import hello.TWITTERPersonRepository;
import hello.TotalPersonRepository;
import hello.TotalUser;
import hello.TwitterUser;

public class Distinct_Tree {

	String[][] test;
	public Distinct_Tree(String[][] rank, FBPersonRepository personRepository, int run) {

		String[][] data = new String[1][Lists.newArrayList(personRepository.findAll().iterator()).size()];
		System.out.print(data.length);
		for (int i = 0; i < Lists.newArrayList(personRepository.findAll().iterator()).size(); i++) {
			data[0][i] = Lists.newArrayList(personRepository.findAll().iterator()).get(i).getUid();
		}
		// run值為相異數規則設置 "m"
		test=Split(rank, data, personRepository, run);
	}
	
	public Distinct_Tree(String[][] rank, TWITTERPersonRepository personRepository, int run) {

		String[][] data = new String[1][Lists.newArrayList(personRepository.findAll().iterator()).size()];
		System.out.print(data.length);
		for (int i = 0; i < Lists.newArrayList(personRepository.findAll().iterator()).size(); i++) {
			data[0][i] = Lists.newArrayList(personRepository.findAll().iterator()).get(i).getUid();
		}
		// run值為相異數規則設置 "m"
		test=Split(rank, data, personRepository, run);
	}
	
	public Distinct_Tree(String[][] rank, TotalPersonRepository personRepository, int run) {

		String[][] data = new String[1][Lists.newArrayList(personRepository.findAll().iterator()).size()];
		System.out.print(data.length);
		for (int i = 0; i < Lists.newArrayList(personRepository.findAll().iterator()).size(); i++) {
			data[0][i] = Lists.newArrayList(personRepository.findAll().iterator()).get(i).getUid();
		}
		// run值為相異數規則設置 "m"
		test=Split(rank, data, personRepository, run);
	}
	
	public String[][] get_Distinct_Tree(){
		return test;
	}

	int run_time = 0;
	String[][] ans;
	
	String[][] Split(String[][] rank, String[][] data, FBPersonRepository personRepository, int run) {
		ArrayList<FbUser> af = Lists.newArrayList(personRepository.findAll().iterator());
		String[][] Sdata = new String[af.size()][af.size()];
		if (run_time < run) {
			if (rank[run_time][0] == "name") {
				int Set_Size = 0, m = 0;
				for (int i = 0; i < data.length; i++) {
					Set<String> Set1 = new HashSet<String>();
					for (int j = 0; j < data[i].length; j++)
						if (data[i][j] != null)
							Set1.add(personRepository.findByUid(data[i][j]).getName());
					Set_Size += Set1.size();
					for (int j = 0; j < Set1.size(); j++) {
						int n = 0;
						for (int k = 0; k < data[i].length; k++) {
							if (data[i][k] != null)
								if (personRepository.findByUid(data[i][k]).getName()
										.equals(Lists.newArrayList(Set1.iterator()).get(j))) {
									Sdata[m][n] = data[i][k];
									n++;
								}
						}
						m++;
					}
				}
				String[][] Rdata = new String[Set_Size][af.size()];
				for (int i = 0; i < Set_Size; i++)
					if (Sdata[i][0] != null)
						Rdata[i] = Sdata[i];
				run_time++;
				ans=Rdata;
				Split(rank, Rdata, personRepository, run);
			} else if (rank[run_time][0] == "email") {
				int Set_Size = 0, m = 0;
				for (int i = 0; i < data.length; i++) {
					Set<String> Set1 = new HashSet<String>();
					for (int j = 0; j < data[i].length; j++)
						if (data[i][j] != null)
							Set1.add(personRepository.findByUid(data[i][j]).getEmail());
					Set_Size += Set1.size();
					for (int j = 0; j < Set1.size(); j++) {
						int n = 0;
						for (int k = 0; k < data[i].length; k++) {
							if (data[i][k] != null)
								if (personRepository.findByUid(data[i][k]).getEmail()
										.equals(Lists.newArrayList(Set1.iterator()).get(j))) {
									Sdata[m][n] = data[i][k];
									n++;
								}
						}
						m++;
					}
				}
				String[][] Rdata = new String[Set_Size][af.size()];
				for (int i = 0; i < Set_Size; i++)
					if (Sdata[i][0] != null)
						Rdata[i] = Sdata[i];
				run_time++;
				ans=Rdata;
				Split(rank, Rdata, personRepository, run);
			} else if (rank[run_time][0] == "location") {
				int Set_Size = 0, m = 0;
				for (int i = 0; i < data.length; i++) {
					Set<String> Set1 = new HashSet<String>();
					for (int j = 0; j < data[i].length; j++) {
						if (data[i][j] != null) 
							Set1.add(personRepository.findByUid(data[i][j]).getLocation());
					}
					Set_Size += Set1.size();
					for (int j = 0; j < Set1.size(); j++) {
						int n = 0;
						for (int k = 0; k < data[i].length; k++) {
							if (data[i][k] != null)
								if (personRepository.findByUid(data[i][k]).getLocation()
										.equals(Lists.newArrayList(Set1.iterator()).get(j))) {
									Sdata[m][n] = data[i][k];
									n++;
								}
						}
						m++;
					}

				}
				String[][] Rdata = new String[Set_Size][af.size()];
				for (int i = 0; i < Set_Size; i++)
					if (Sdata[i][0] != null)
						Rdata[i] = Sdata[i];
				run_time++;
				ans=Rdata;
				Split(rank, Rdata, personRepository, run);
			} else if (rank[run_time][0] == "birthday") {
				int Set_Size = 0, m = 0;
				for (int i = 0; i < data.length; i++) {
					Set<String> Set1 = new HashSet<String>();
					for (int j = 0; j < data[i].length; j++)
						if (data[i][j] != null)
							Set1.add(personRepository.findByUid(data[i][j]).getBirthday());
					Set_Size += Set1.size();
					for (int j = 0; j < Set1.size(); j++) {
						int n = 0;
						for (int k = 0; k < data[i].length; k++) {
							if (data[i][k] != null)
								if (personRepository.findByUid(data[i][k]).getBirthday()
										.equals(Lists.newArrayList(Set1.iterator()).get(j))) {
									Sdata[m][n] = data[i][k];
									n++;
								}
						}
						m++;
					}
				}
				String[][] Rdata = new String[Set_Size][af.size()];
				for (int i = 0; i < Set_Size; i++)
					if (Sdata[i][0] != null)
						Rdata[i] = Sdata[i];
				run_time++;
				ans=Rdata;
				Split(rank, Rdata, personRepository, run);
			} else if (rank[run_time][0] == "uid") {
				// ...
			} else if (rank[run_time][0] == "gender") {
				int Set_Size = 0, m = 0;
				for (int i = 0; i < data.length; i++) {
					Set<String> Set1 = new HashSet<String>();
					for (int j = 0; j < data[i].length; j++)
						Set1.add(personRepository.findByUid(data[i][j]).getGender());
					Set_Size += Set1.size();
					for (int j = 0; j < Set1.size(); j++) {
						int n = 0;
						for (int k = 0; k < data[i].length; k++) {
							if (personRepository.findByUid(data[i][k]).getGender()
									.equals(Lists.newArrayList(Set1.iterator()).get(j))) {
								Sdata[m][n] = data[i][k];
								n++;
							}
						}
						m++;
					}

				}
				String[][] Rdata = new String[Set_Size][af.size()];
				for (int i = 0; i < Set_Size; i++) {
					System.out.println("Sdata[" + i + "][0]" + Sdata[i][0]);
					if (Sdata[i][0] != null) {
						Rdata[i] = Sdata[i];
					}
				}
				run_time++;
				ans=Rdata;
				Split(rank, Rdata, personRepository, run);
			}
		}
		return ans;
	};


	String[][] Split(String[][] rank, String[][] data, TWITTERPersonRepository personRepository, int run) {
		ArrayList<TwitterUser> af = Lists.newArrayList(personRepository.findAll().iterator());
		String[][] Sdata = new String[af.size()][af.size()];
		if (run_time < run) {
			if (rank[run_time][0] == "name") {
				//...
			} else if (rank[run_time][0] == "email") {
				//...
			} else if (rank[run_time][0] == "location") {
				int Set_Size = 0, m = 0;
				for (int i = 0; i < data.length; i++) {
					Set<String> Set1 = new HashSet<String>();
					for (int j = 0; j < data[i].length; j++) {
						if (data[i][j] != null) {
							Set1.add(personRepository.findByUid(data[i][j]).getLocation());
						}
					}
					Set_Size += Set1.size();
					for (int j = 0; j < Set1.size(); j++) {
						System.out.println(Lists.newArrayList(Set1.iterator()).get(j));
						int n = 0;
						for (int k = 0; k < data[i].length; k++) {
							if (data[i][k] != null)
								if (personRepository.findByUid(data[i][k]).getLocation()
										.equals(Lists.newArrayList(Set1.iterator()).get(j))) {
									Sdata[m][n] = data[i][k];
									n++;
								}
						}
						m++;
					}
				}
				String[][] Rdata = new String[Set_Size][af.size()];
				for (int i = 0; i < Set_Size; i++)
					if (Sdata[i][0] != null)
						Rdata[i] = Sdata[i];
				run_time++;
				ans=Rdata;
				Split(rank, Rdata, personRepository, run);
			} else if (rank[run_time][0] == "birthday") {
				//...
			} else if (rank[run_time][0] == "uid") {
				// ...
			} else if (rank[run_time][0] == "gender") {
				int Set_Size = 0, m = 0;
				for (int i = 0; i < data.length; i++) {
					Set<String> Set1 = new HashSet<String>();
					for (int j = 0; j < data[i].length; j++)
						Set1.add(personRepository.findByUid(data[i][j]).getGender());
					Set_Size += Set1.size();
					for (int j = 0; j < Set1.size(); j++) {
						int n = 0;
						for (int k = 0; k < data[i].length; k++) {
							if (personRepository.findByUid(data[i][k]).getGender()
									.equals(Lists.newArrayList(Set1.iterator()).get(j))) {
								Sdata[m][n] = data[i][k];
								n++;
							}
						}
						m++;
					}
				}
				String[][] Rdata = new String[Set_Size][af.size()];
				for (int i = 0; i < Set_Size; i++) {
					if (Sdata[i][0] != null) {
						Rdata[i] = Sdata[i];
					}
				}
				run_time++;
				ans=Rdata;
				Split(rank, Rdata, personRepository, run);
			}
		}
		return ans;
	};

	String[][] Split(String[][] rank, String[][] data, TotalPersonRepository personRepository, int run) {
		ArrayList<TotalUser> af = Lists.newArrayList(personRepository.findAll().iterator());
		String[][] Sdata = new String[af.size()][af.size()];
		if (run_time < run) {
			if (rank[run_time][0] == "name") {
				//...
			} else if (rank[run_time][0] == "email") {
				//...
			} else if (rank[run_time][0] == "location") {
				int Set_Size = 0, m = 0;
				for (int i = 0; i < data.length; i++) {
					Set<String> Set1 = new HashSet<String>();
					for (int j = 0; j < data[i].length; j++) {
						// System.out.println("---------!!!!----------data["+i+"]["+j+"] :" +
						// data[i][j]);
						if (data[i][j] != null) {
							Set1.add(personRepository.findByUid(data[i][j]).getLocation());
						}
					}
					System.out.println("---------!!!!----------location :" + Set1);

					Set_Size += Set1.size();
					for (int j = 0; j < Set1.size(); j++) {
						System.out.println(Lists.newArrayList(Set1.iterator()).get(j));
						int n = 0;
						for (int k = 0; k < data[i].length; k++) {
							if (data[i][k] != null)
								if (personRepository.findByUid(data[i][k]).getLocation()
										.equals(Lists.newArrayList(Set1.iterator()).get(j))) {
									Sdata[m][n] = data[i][k];
									n++;
								}
						}
						m++;
					}

				}
				String[][] Rdata = new String[Set_Size][af.size()];
				for (int i = 0; i < Set_Size; i++)
					if (Sdata[i][0] != null)
						Rdata[i] = Sdata[i];
				run_time++;
				System.out.println("---------!!!!----------Rdata.length :" + Rdata.length);
				ans=Rdata;
				Split(rank, Rdata, personRepository, run);
			} else if (rank[run_time][0] == "birthday") {
				//...
			} else if (rank[run_time][0] == "uid") {
				// ...
			} else if (rank[run_time][0] == "gender") {
				int Set_Size = 0, m = 0;
				for (int i = 0; i < data.length; i++) {
					Set<String> Set1 = new HashSet<String>();
					for (int j = 0; j < data[i].length; j++)
						Set1.add(personRepository.findByUid(data[i][j]).getGender());
					System.out.println("---------!!!!----------gender :" + Set1);
					Set_Size += Set1.size();
					for (int j = 0; j < Set1.size(); j++) {
						System.out.println(Lists.newArrayList(Set1.iterator()).get(j));
						int n = 0;
						for (int k = 0; k < data[i].length; k++) {
							if (personRepository.findByUid(data[i][k]).getGender()
									.equals(Lists.newArrayList(Set1.iterator()).get(j))) {
								Sdata[m][n] = data[i][k];
								n++;
							}
						}
						System.out.println("******Sdata[" + m + "][0]:" + Sdata[m][0]);
						m++;
					}
				}
				String[][] Rdata = new String[Set_Size][af.size()];
				System.out.println("Sdata.length:" + Sdata.length);
				System.out.println("Set_Size:" + Set_Size);
				for (int i = 0; i < Set_Size; i++) {
					System.out.println("Sdata[" + i + "][0]" + Sdata[i][0]);
					if (Sdata[i][0] != null) {
						Rdata[i] = Sdata[i];
					}
				}
				run_time++;
				System.out.println("---------!!!!----------Rdata.length :" + Rdata.length);
				ans=Rdata;
				Split(rank, Rdata, personRepository, run);
			}
		}
		return ans;
	};

}
