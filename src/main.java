import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.HashMap;

public class main {
	
	public static int Value = 10000000;
	public static int PATTERN_LENGTH = 20;
	static HashMap<String, PatternInfo> pattern = new HashMap<String, PatternInfo>();

	public static void main(String[] args) {
		FileManager fm = new FileManager();
		fm.ReadTrainingData("C:\\Alg_SERVER_DATA");
		BasicInformation t = fm.readBasicInformation("C:\\Alg_CLIENT_DATA\\Basicinformation.txt");
		
		TraningPattern tp = new TraningPattern();
		pattern = tp.makeCandle();
		
		CompanyManager.getInstance().refreshCompany();

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
		
		System.out.println(main.Value+"");
	}

}
