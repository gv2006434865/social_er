package tree;

import java.math.BigDecimal;

public class Tree {
	int run, success, fail;
	double Coverage, entropy;

	public Tree(int run, int success, int fail) {
		this.run = run;
		this.success = success;
		this.fail = fail;
	}

	public Tree(int success, int fail) {
		this.success = success;
		this.fail = fail;
	}

	public int getRun() {
		return run;
	}

	/*
	 * public float getCoverage() { BigDecimal bd = new BigDecimal((double)
	 * Coverage); bd = bd.setScale(2, 4); Coverage = bd.floatValue(); return
	 * Coverage; }
	 */

	public int getSuccess() {
		return success;
	}

	public int getFail() {
		return fail;
	}

	public double getCoverage() {
		this.Coverage = (double) success / ((double) success + (double) fail);

		return Coverage;
	}

	public double getEntropy() {
		double a = success, b = fail;
		if(b / (a + b) == 0.0) return 0.0;
		this.entropy = -(a / (a + b)) * Math.log(a / (a + b)) / Math.log(2)
				- (b / (a + b)) * Math.log(b / (a + b)) / Math.log(2);
		return entropy;
	}

	public double getPoint() {
		double a = success, b = fail;		
		this.Coverage = (double) success / ((double) success + (double) fail);
		if(b / (a + b) == 0.0) return 0.0;
		this.entropy = -(a / (a + b)) * Math.log(a / (a + b)) / Math.log(2) - (b / (a + b)) * Math.log(b / (a + b)) / Math.log(2);
		double p = entropy * Coverage;
		if(Double.toString(p) == "NaN") p=0;
		
		return p;
	}
	
}
