public class StockInfo {
	private int value;
	private int volume;

	public StockInfo(int value, int volume) {
		this.value = value;
		this.volume = volume;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

}
