package hello;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;

public class test {

	public static void main(String[] args) {
		String str1 = "gv147258369@yahoo.com.tw";
		String str2 = "gv2006434865@yahoo.com.tw";
		StringMetric jaro = StringMetrics.jaroWinkler();
		StringMetric leve = StringMetrics.levenshtein();
		float result1 = jaro.compare(str1, str2);
		float result2 = leve.compare(str1, str2);
		System.out.println(result1); // 0.8513158
		System.out.println(result2); // 0.57894737

		String[] a = { "林佳蓉" };
		int wordlength = a[0].length();
		char[] y = a[0].toCharArray();
		for (int i = 0; i < wordlength; i++)
			System.out.println(y[i]);
	}

}
