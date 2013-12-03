import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

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

		while (Msg.equals("END") == false) {
			if (socket.isConnected()) {
				try {
					pw.println("TICK");
					pw.flush();

					Msg = br.readLine();
					PrintWriter t = new PrintWriter(System.out);
					t.println(Msg);
					

					Thread.sleep(freq);
					cnt++;
				} catch (Exception e) {

				}
			}
		}
	}
	
	public void CloseConnection(){
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
	
	public void Wait(){
		try {
			br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
