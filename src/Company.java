import java.util.ArrayList;

public class Company {
	private ArrayList<DayTick> candle;
	private ArrayList<Tick> tempTick;
	private String tempTickDate;
	private String name;
	private double buyValue;
	ArrayList<StockInfo> hasStock;

	public Company() {
		candle = new ArrayList<DayTick>();
		tempTick = new ArrayList<Tick>();
		hasStock = new ArrayList<StockInfo>();
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

	public void makeTempTickToDayTick() {
		if (tempTick.size() == 0)
			return;
		DayTick temp = new DayTick();
		temp.makeDayTick(tempTick);
		candle.add(temp);
		tempTick.clear();
	}

	public void addTempTick(Tick tick) {
		tempTick.add(tick);
		tempTickDate = tick.getDate();
	}

	public String getTempTickDate() {
		return tempTickDate;
	}

	public String makeDayTickPattern(int size) {
		StringBuilder returnString = new StringBuilder();
		int end = candle.size();
		if (end > size)
			end = size;
		for (int i = 0; i < end; i++) {
			returnString.append("_" + candle.get(i).getType());
		}
		return returnString.toString();

	}

	public double getBuyValue() {
		return buyValue;
	}

	public void setBuyValue(double buyValue) {
		this.buyValue = buyValue;
	}

	public void buyStock(int value, int amount) {
		hasStock.add(new StockInfo(value, amount));
	}

	public int calSellStockValue(int value) {
		int sum = 0;
		for (StockInfo t : hasStock) {
			sum += (value - t.getValue()) * t.getVolume();
		}
		return sum;
	}

	public int getVolume() {
		int sum = 0;
		for (StockInfo t : hasStock) {
			sum += t.getVolume();
		}
		return sum;

	}

	public void sellStock() {
		hasStock.clear();
	}
}
