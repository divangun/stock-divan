public class PatternInfo {

	private double plusCnt;
	private double minusCnt;
	private double powerPlusCnt;
	private double powerMinusCnt;
	private double normalCnt;
	private String pattern;

	public PatternInfo() {
		pattern = null;
		plusCnt = 0;
		powerMinusCnt = 0;
		powerPlusCnt = 0;
		minusCnt = 0;
		normalCnt = 0;
	}

	public PatternInfo(String pattern, double plus, double minus, double powerPlus,
			double powerMinus, double normal) {
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
	
	public void addPlus(double value){
		plusCnt += value;
	}

	public void addPowerPlus() {
		powerPlusCnt += 1;
	}
	public void addPowerPlus(double value) {
		powerPlusCnt += value;
	}

	public void addMinus() {
		minusCnt += 1;
	}
	
	public void addMinus(double value) {
		minusCnt += value;
	}


	public void addPowerMinus() {
		powerMinusCnt += 1;
	}
	public void addPowerMinus(double value) {
		powerMinusCnt += value;
	}

	public void addNormal() {
		normalCnt += 1;
	}

	public void addPatternInfo(float value, PatternInfo a) {
		this.plusCnt += a.plusCnt*a.plusCnt * value;
		this.minusCnt += a.minusCnt*a.minusCnt * value;
		this.powerPlusCnt += a.powerPlusCnt*a.powerPlusCnt * value;
		this.powerMinusCnt += a.powerMinusCnt*a.powerMinusCnt * value;
		this.normalCnt += a.normalCnt*a.normalCnt * value;
	}

	public double calculatePatternValue() {
		return (powerPlusCnt + plusCnt - minusCnt - powerMinusCnt * 3) / (plusCnt / 10);

	}
	
}
