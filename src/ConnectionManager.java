import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;

public class ConnectionManager {

	private Socket socket;

	private OutputStream os = null;
	private OutputStreamWriter osw = null;
	private InputStream is = null;
	private InputStreamReader isr = null;
	private BufferedReader br = null;
	private PrintWriter pw = null;
	private String ip;
	private int port;

	public ConnectionManager() {

	}

	public void connection(String ip, int port) {
		this.ip = ip;
		this.port = port;
		try {
			this.socket = new Socket(ip, port);
			this.os = socket.getOutputStream();
			this.is = socket.getInputStream();
			osw = new OutputStreamWriter(os);
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			pw = new PrintWriter(osw);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void SendAndReceive(int freq) {
		int cnt = 0;
		String Msg = "TICK";
		String date = null;
		while (Msg.equals("END") == false) {
			if (socket.isConnected()) {
				try {
					pw.println("TICK");
					pw.flush();

					Msg = br.readLine();
					Company com;
					Tick t = new Tick(Msg);
					if (CompanyManager.getInstance()
							.containCompany(t.getCode())) {
						com = CompanyManager.getInstance().getCompany(
								t.getCode());
						if (!com.getTempTickDate().equals(t.getDate())) {
							com.makeTempTickToDayTick();
							if (com.getCandle().size() > 10) {
								PatternInfo patternInfo = pattenCompare(com);
								com.setBuyValue(patternInfo
										.calculatePatternValue());
								System.out.println(com.getBuyValue()+"/"+t.getPrice());

							}
						}
						com.addTempTick(t);

						if (com.getBuyValue() > 2.7) {
							if (com.getTempTickLength() > 10) {
								if (com.isIncrease() || t.getTime() == 150000) {
									int amount =(int)(((com.getBuyValue()-2.0)*10000000) / t.getPrice());
									if(amount*t.getPrice() > main.Value) amount = main.Value / t.getPrice();
									if(amount > com.getVolume()){
										amount -= com.getVolume();
										com.buyStock(t.getPrice(), amount);
										String write = t.getCode()+"/B/"+amount;
										System.out.println("Buy");
										System.out.println(write+"/"+t.getPrice());
										pw.println(write);
										pw.flush();
										
										main.Value -= t.getPrice()*amount*1.0033;
									}
								}
							}
						}
						if (com.hasStock.size() > 0) {
							if(com.getBuyValue() < 2.4){
								int sellValue = com.calSellStockValue(t.getPrice());
								String write = t.getCode()+"/S/"+com.getVolume();
								System.out.println("Sell");
								System.out.println(write+"/"+t.getPrice());
								int tempValue = main.Value;
								pw.println(write);
								pw.flush();
								main.Value += sellValue;
								com.sellStock();
							}

						}

					} else {
						com = new Company();
						com.setName(t.getCode());
						com.addTempTick(t);
						CompanyManager.getInstance().addCompany(com);
					}
					date = t.getDate();

					Thread.sleep(freq);
					cnt++;
				} catch (Exception e) {

				}
			}
		}
	}

	public void CloseConnection() {
		try {
			this.socket.close();
			pw.close();
			br.close();
			osw.close();
			isr.close();
			is.close();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Wait() {
		try {
			br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PatternInfo pattenCompare(Company com) {
		Iterator<String> iterator = main.pattern.keySet().iterator();
		PatternInfo patternInfo = new PatternInfo();
		float temp = 0;
		while (iterator.hasNext()) {
			String trainedPattern = iterator.next();
			String nowPattern = com
					.makeDayTickPattern(trainedPattern.length() / 4);
			float patternSmility = evalueatePattern(trainedPattern, nowPattern);
			if (patternSmility > 10)
				patternInfo.addPatternInfo(patternSmility,
						main.pattern.get(trainedPattern));

		}
		return patternInfo;
	}

	public float evalueatePattern(String pattern, String pattern2) {
		float[][] smithWarman = new float[pattern.length() + 1][pattern2
				.length() + 1];
		float max = 0;
		for (int i = 0; i < pattern.length(); i += 4) {
			for (int j = 0; j < pattern2.length(); j += 4) {
				float genereSmlity = genereSmility(pattern.substring(i, i + 4),
						pattern2.substring(j, j + 4));
				if (smithWarman[(i / 4) + 1][(j / 4) + 1] < smithWarman[(i / 4)][(j / 4)]
						+ genereSmlity) {
					smithWarman[(i / 4) + 1][(j / 4) + 1] = (smithWarman[(i / 4)][(j / 4)] + genereSmlity);
				}

				if (smithWarman[(i / 4) + 1][(j / 4) + 1] < smithWarman[(i / 4)][(j / 4) + 1] - 1)
					smithWarman[(i / 4) + 1][(j / 4) + 1] = smithWarman[(i / 4)][(j / 4) + 1] - 1;
				if (smithWarman[(i / 4) + 1][(j / 4) + 1] < smithWarman[(i / 4) + 1][(j / 4)] - 1)
					smithWarman[(i / 4) + 1][(j / 4) + 1] = smithWarman[(i / 4)][(j / 4) + 1] - 1;

				if (smithWarman[(i / 4) + 1][(j / 4) + 1] > max)
					max = smithWarman[(i / 4) + 1][(j / 4) + 1];
			}
		}
		return max;
	}

	public float genereSmility(String a, String b) {
		float fullValue = -1;
		if (a.charAt(1) == b.charAt(1))
			fullValue += 2;
		if (a.charAt(2) == b.charAt(2))
			fullValue += 0.4;
		if (a.charAt(3) == b.charAt(3))
			fullValue += 0.6;
		return fullValue;
	}

}
