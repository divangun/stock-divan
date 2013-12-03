import java.util.ArrayList;

public class Company {
	private ArrayList<DayTick> candle;
	private String name;

	public Company() {
	}

	public Company(String name) {
		this.setName(name);
	}

	public ArrayList<DayTick> getCandle() {
		return candle;
	}

	public void setCandle(ArrayList<DayTick> candle) {
		this.candle = candle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
