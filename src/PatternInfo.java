public class PatternInfo {

	private int plusCnt;
	private int minusCnt;
	private int powerPlusCnt;
	private int powerMinusCnt;
	private int normalCnt;
	private String pattern;
	
	public PatternInfo() {
		pattern = null;
		plusCnt = 0;
		powerMinusCnt = 0;
		powerPlusCnt = 0;
		minusCnt = 0;
		normalCnt = 0;
	}

	public PatternInfo(String pattern, int plus, int minus, int powerPlus, int powerMinus, int normal) {
		this.pattern = pattern;
		plusCnt = plus;
		minusCnt = minus;
		powerMinusCnt = powerMinus;
		powerPlusCnt = powerPlus;
		normalCnt = normal;
	}

	public void addPlus() {
		plusCnt += 1;
	}
	
	public void addPowerPlus(){
		powerPlusCnt +=1;
	}

	public void addMinus() {
		minusCnt += 1;
	}

	public void addPowerMinus(){
		powerMinusCnt += 1;
	}
	
	public void addNormal(){
		normalCnt += 1;
	}

}
