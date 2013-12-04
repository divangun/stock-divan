import java.util.ArrayList;
import java.util.HashMap;

public class TraningPattern {
	static HashMap<String, PatternInfo> pattern = new HashMap<String, PatternInfo>();

	public TraningPattern() {

	}

	public HashMap<String, PatternInfo> makeCandle() {
		CompanyManager t = CompanyManager.getInstance();

		ArrayList<String> companyList = t.getCompanyList();

		for (String companyString : companyList) {
			Company com = t.getCompany(companyString);
			ArrayList<DayTick> companyCandle = com.getCandle();
			for (int i = 0; i < companyCandle.size(); i++) {
				DayTick nowDay = companyCandle.get(i);
				for (int j = 0; j < i; j++) {
					DayTick cfDay = companyCandle.get(j);
					if (nowDay.getSellMax() < nowDay.getAverage()
							- cfDay.getAverage()) {
						nowDay.setSellMax(nowDay.getAverage()
								- cfDay.getAverage());
						nowDay.setSellMaxDay(j);
					}
					if (cfDay.getBuyMax() < nowDay.getAverage()
							- cfDay.getAverage()) {
						cfDay.setBuyMax(nowDay.getAverage()
								- cfDay.getAverage());
						cfDay.setBuyMaxDay(i);
					}
					if (cfDay.getBuyMin() > nowDay.getAverage()
							- cfDay.getAverage()) {
						cfDay.setBuyMin(nowDay.getAverage()
								- cfDay.getAverage());
						cfDay.setBuyMinDay(i);
					}
					if (nowDay.getSellMin() > nowDay.getAverage()
							- cfDay.getAverage()) {
						nowDay.setSellMin(nowDay.getAverage()
								- cfDay.getAverage());
						nowDay.setSellMinDay(j);
					}

				}
			}
			for (int i = 0; i < companyCandle.size(); i++) {
				DayTick nowDay = companyCandle.get(i);
				makePattern(nowDay.getSellMaxDay() - 5, i, nowDay.getSellMax(),
						companyCandle, 1.1, 2);
				makePattern(nowDay.getSellMinDay() - 5, i, nowDay.getSellMin(),
						companyCandle, 2.2, 1);
				makePattern(i - 5, nowDay.getBuyMaxDay(), nowDay.getBuyMax(),
						companyCandle, 1.1, 2);
				makePattern(i - 5, nowDay.getBuyMinDay(), nowDay.getBuyMin(),
						companyCandle, 2.2, 1);
			}
		}
		return pattern;
	}

	public void makePattern(int start, int end, int value, ArrayList<DayTick> companyCandle, double plusWeight, double minusWeight) {
		if(value == 0x7fffffff || value == -100000) return;
		String patternString = "";
		PatternInfo temp;
		if (start < 0)
			start = 0;
		for (int i = 0; i <= end; i++) {
			patternString+= "_" + companyCandle.get(i).getType();
		}
		if (value > companyCandle.get(start).getAverage() * 0.3) {
			temp = new PatternInfo(patternString, 0, 0, plusWeight, 0, 0);
			if (pattern.containsKey(patternString)) {
				pattern.get(patternString).addPowerPlus(plusWeight);
			} else
				pattern.put(patternString, temp);
		}
		if (value < companyCandle.get(start).getAverage() * 0.2 * -1) {
			temp = new PatternInfo(patternString, 0, 0, 0, minusWeight*2, 0);
			if (pattern.containsKey(patternString)) {
				pattern.get(patternString).addPowerMinus(minusWeight*2);
			} else
				pattern.put(patternString, temp);
		}
		if (value > companyCandle.get(start).getAverage() * 0.033) {
			temp = new PatternInfo(patternString, plusWeight, 0, 0, 0, 0);
			if (pattern.containsKey(patternString)) {
				pattern.get(patternString).addPlus(plusWeight);
			} else
				pattern.put(patternString, temp);
		}
		if (value < companyCandle.get(start).getAverage() * 0.033) {
			temp = new PatternInfo(patternString, 0, minusWeight, 0, 0, 0);
			if (pattern.containsKey(patternString)) {
				pattern.get(patternString).addMinus(minusWeight);
			} else
				pattern.put(patternString, temp);
		}

	}
}
