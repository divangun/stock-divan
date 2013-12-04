import java.util.ArrayList;
import java.util.HashMap;

public class main {

	public static int PATTERN_LENGTH = 20;
	static HashMap<String, PatternInfo> pattern = new HashMap<String, PatternInfo>();

	public static void main(String[] args) {
		FileManager fm = new FileManager();
		fm.ReadTrainingData("C:\\Alg_SERVER_DATA");
		BasicInformation t = fm.readBasicInformation("C:\\Alg_CLIENT_DATA\\Basicinformation.txt");
		makeCandle();
		CompanyManager.getInstance().refreshCompany();

		ConnectionManager con = new ConnectionManager();
		con.connection(t.getIPaddress(), t.getPort());
		con.SendAndReceive(t.getFeq());
		con.Wait();

	}

	public static void makeCandle() {
		CompanyManager t = CompanyManager.getInstance();

		ArrayList<String> companyList = t.getCompanyList();

		for (String companyString : companyList) {
			Company com = t.getCompany(companyString);
			ArrayList<DayTick> companyCandle = com.getCandle();
			for (int i = 0; i < companyCandle.size(); i++) {
				DayTick nowDay = companyCandle.get(i);
				for (int j = 0; j < i; j++) {
					DayTick cfDay = companyCandle.get(j);
					if (nowDay.getSellMax() < nowDay.getAverage() - cfDay.getAverage()) {
						nowDay.setSellMax(nowDay.getAverage() - cfDay.getAverage());
						nowDay.setSellMaxDay(j);
					}
					if (cfDay.getBuyMax() < nowDay.getAverage() - cfDay.getAverage()) {
						cfDay.setBuyMax(nowDay.getAverage() - cfDay.getAverage());
						cfDay.setBuyMax(i);
					}
					if (cfDay.getBuyMin() > nowDay.getAverage() - cfDay.getAverage()) {
						cfDay.setBuyMin(nowDay.getAverage() - cfDay.getAverage());
						cfDay.setBuyMin(i);
					}
					if (nowDay.getSellMin() > nowDay.getAverage() - cfDay.getAverage()) {
						nowDay.setSellMin(nowDay.getAverage() - cfDay.getAverage());
						cfDay.setSellMax(j);
					}


				}
			}
			for(int i=0;i<companyCandle.size();i++){
				DayTick nowDay = companyCandle.get(i);
				makePattern(nowDay.getSellMaxDay()-5, i, nowDay.getSellMax());
				
			}
			
			

//			for (int i = 0; i < companyCandle.size(); i++) {
//				String type = "";
//				DayTick cfDay = companyCandle.get(i);
//				for (int j = i; j < i + PATTERN_LENGTH
//						&& j < companyCandle.size(); j++) {
//					PatternInfo temp;
//					DayTick nowDay = companyCandle.get(j);
//					type = type + "_" + nowDay.getType();
//
//					int value = (nowDay.getStart()+nowDay.getEnd())/2 - (cfDay.getStart()+cfDay.getEnd())/2;
//					if (Math.abs(value) < 2) {
//						temp = new PatternInfo(type, 0, 0, 0, 0, 1);
//						if (pattern.containsKey(type)) {
//							pattern.get(type).addNormal();
//						} else
//							pattern.put(type, temp);
//					} else if (Math.abs(value) > nowDay.getMax() * 0.4) {
//						if (value < 0) {
//							temp = new PatternInfo(type, 0, 0, 0, 1, 0);
//							if (pattern.containsKey(type)) {
//								pattern.get(type).addPowerMinus();
//							} else
//								pattern.put(type, temp);
//						} else {
//							temp = new PatternInfo(type, 0, 0, 1, 0, 0);
//							if (pattern.containsKey(type)) {
//								pattern.get(type).addPowerPlus();
//							} else
//								pattern.put(type, temp);
//						}
//
//					} else if(Math.abs(value) > nowDay.getMax()*0.033){
//						if (value < 0) {
//							temp = new PatternInfo(type, 0, 1, 0, 0, 0);
//							if (pattern.containsKey(type)) {
//								pattern.get(type).addMinus();
//							} else
//								pattern.put(type, temp);
//						} else {
//							temp = new PatternInfo(type, 1, 0, 0, 0, 0);
//							if (pattern.containsKey(type)) {
//								pattern.get(type).addPlus();
//							} else
//								pattern.put(type, temp);
//						}
//
//					}
//
//				}
//			}
		}
	}
	public static void makePattern(int start, int end, int value){
		if(start < 0 ) start = 0;
		for(int i = 0 ; i <= end ; i++){
			
		}
		
	}
}
