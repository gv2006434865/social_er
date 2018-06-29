package Integration;

public class TD_Array {
	float[][] TD;
	int[] three;
	String[][] TD_Str;
	public TD_Array(float[][] data, String[][] str, int[] three) {
		this.TD = data; //權重
		this.three =three; //倒數的數，為了控制陣列第三維的計算
		this.TD_Str = str; //內容
	}
	
	public int getThree_Length(){
		return three.length;
	}
}
