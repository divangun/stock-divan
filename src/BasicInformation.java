public class BasicInformation {
	private String IPaddress;
	private int port;
	private int company;
	private int feq;

	public BasicInformation() {
		IPaddress = "";
		port = 0;
		company = 0;
		feq = 0;
	}

	public BasicInformation(String IPaddress, int port, int company, int feq) {
		this.IPaddress = IPaddress;
		this.port = port;
		this.company = company;
		this.feq = feq;
	}

	public String getIPaddress() {
		return IPaddress;
	}

	public void setIPaddress(String iPaddress) {
		IPaddress = iPaddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getCompany() {
		return company;
	}

	public void setCompany(int company) {
		this.company = company;
	}

	public int getFeq() {
		return feq;
	}

	public void setFeq(int feq) {
		this.feq = feq;
	}

}
