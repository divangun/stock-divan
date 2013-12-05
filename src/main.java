import java.util.HashMap;

public class main {
	
	public static int Value = 10000000;
	public static int PATTERN_LENGTH = 20;
	static HashMap<String, PatternInfo> pattern = new HashMap<String, PatternInfo>();

	public static void main(String[] args) {
		FileManager fm = new FileManager();
//		fm.ReadTrainingData("Train");
		fm.ReadTrainingData("C:\\Alg_SERVER_DATA\\Train");

		TraningPattern tp = new TraningPattern();
		pattern = tp.makeCandle();
		System.out.println("Make pattern Ok");

		CompanyManager.getInstance().refreshCompany();
		//Traning End
		BasicInformation t = fm.readBasicInformation("C:\\Alg_CLIENT_DATA\\Basicinformation.txt");
		System.out.println("read Basicinformation Ok");
		ConnectionManager con = new ConnectionManager();
		con.connection(t.getIPaddress(), t.getPort());
		con.SendAndReceive(t.getFeq());
//		con.Wait();
		
		for(String company : CompanyManager.getInstance().getCompanyList()){
			Company c = CompanyManager.getInstance().getCompany(company);
			if(c.hasStock.size() > 0){
				main.Value += c.calSellStockValue(c.getLastTick());
			}
			
		}
	}

}
