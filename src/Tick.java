
public class Tick {
	private String code;
	private String date;
	private int time;
	private int volume;
	private int price;

	
	
	public Tick(String data){
		String[] temp = data.split("/");

		code = temp[0];
		date = temp[1];
		time = Integer.parseInt(temp[2]);
		price = Integer.parseInt(temp[3]);
		volume = Integer.parseInt(temp[4]);
	}
	
	public Tick(String code, String date, int time, int volume, int price){
		this.code = code;
		this.date = date;
		this.time = time;
		this.volume = volume;
		this.price = price;
		
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	

}
