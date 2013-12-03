import java.util.ArrayList;

public class DayTick {

	public static int PLUS_LONG = 111;
	public static int PLUS_LONG_NO_TAIL = 112;
	public static int PLUS_LONG_UP_TAIL = 113;
	public static int PLUS_LONG_DOWN_TAIL = 114;

	public static int PLUS_SHORT = 121;
	public static int PLUS_SHORT_NO_TAIL = 122;
	public static int PLUS_SHORT_UP_TAIL = 123;
	public static int PLUS_SHORT_DOWN_TAIL = 124;

	public static int MINUS_LONG = 231;
	public static int MINUS_LONG_NO_TAIL = 232;
	public static int MINUS_LONG_UP_TAIL = 233;
	public static int MINUS_LONG_DOWN_TAIL = 234;

	public static int MINUS_SHORT = 241;
	public static int MINUS_SHORT_NO_TAIL = 242;
	public static int MINUS_SHORT_UP_TAIL = 243;
	public static int MINUS_SHORT_DOWN_TAIL = 244;

	public static int CROSS = 301;
	public static int LINE = 302;
	public static int DOWN_TAIL = 303;
	public static int UP_TAIL = 304;

	public static int PLUS = 999;
	public static int MINUS = 888;

	private int max;
	private int min;
	private int start;
	private int end;
	private int volume;
	private int type;

	private int sellMax;
	private int buyMax;
	private int sellDay;
	private int buyDay;

	private String date;

	private int beforeType;

	public DayTick() {
		max = 0;
		min = 0x7ffffff;
		sellMax = buyMax = -100000;
		start = 0;
		end = 0;
		volume = 0;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getStart() {
		return start;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getEnd() {
		return end;
	}

	public int getVolume() {
		return volume;
	}

	public void addVolume(int volume) {
		this.volume += volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		if (max > this.max)
			this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		if (min < this.min)
			this.min = min;
	}

	public void calType() {

		if (start > end) {
			if ((start - end) / start >= 0.015) {
				if ((max - start) < 10 && (end - min) < 10)
					type = DayTick.MINUS_LONG_NO_TAIL;
				else if ((max - start) < 10)
					type = DayTick.MINUS_LONG_DOWN_TAIL;
				else if ((end - min) < 10)
					type = DayTick.MINUS_LONG_UP_TAIL;
				else
					type = DayTick.MINUS_LONG;
			} else {
				if ((max - start) < 10 && (end - min) < 10)
					type = DayTick.MINUS_SHORT_NO_TAIL;
				else if ((max - start) < 10)
					type = DayTick.MINUS_SHORT_DOWN_TAIL;
				else if ((end - min) < 10)
					type = DayTick.MINUS_SHORT_UP_TAIL;
				else
					type = DayTick.MINUS_SHORT;
			}

		}
		if (start < end) {
			if ((end - start) / start >= 0.015)
				if ((max - end) < 10 && (start - min) < 10)
					type = DayTick.PLUS_LONG_NO_TAIL;
				else if ((max - end) < 10)
					type = DayTick.PLUS_LONG_DOWN_TAIL;
				else if ((start - min) < 10)
					type = DayTick.PLUS_LONG_UP_TAIL;
				else
					type = DayTick.PLUS_LONG;
			else {
				if ((max - end) < 10 && (start - min) < 10)
					type = DayTick.PLUS_SHORT_NO_TAIL;
				else if ((max - end) < 10)
					type = DayTick.PLUS_SHORT_DOWN_TAIL;
				else if ((start - min) < 10)
					type = DayTick.PLUS_SHORT_UP_TAIL;
				else
					type = DayTick.PLUS_SHORT;
			}
		}
		if (start == end) {
			if (max - start > 10 && start - min > 10)
				type = DayTick.CROSS;
			else if (max - start > 10)
				type = DayTick.UP_TAIL;
			else if (start - min > 10)
				type = DayTick.DOWN_TAIL;
			else
				type = DayTick.LINE;
		}

	}

	public void calBType(DayTick before) {
		if (before.end > this.end)
			beforeType = DayTick.MINUS;
		else
			beforeType = DayTick.PLUS;
	}

	public void makeDayTick(ArrayList<Tick> tick) {
		for (Tick e : tick) {
			this.date = e.getDate();
			if (this.start == 0)
				this.start = e.getPrice();
			this.end = e.getPrice();
			this.volume += e.getVolume();
			this.setMax(e.getPrice());
			this.setMin(e.getPrice());
		}
		calType();
	}

	public int getSellMax() {
		return sellMax;
	}

	public void setSellMax(int sellMax) {
		this.sellMax = sellMax;
	}

	public int getBuyMax() {
		return buyMax;
	}

	public void setBuyMax(int buyMax) {
		this.buyMax = buyMax;
	}

	public int getSellDay() {
		return sellDay;
	}

	public void setSellDay(int sellDay) {
		this.sellDay = sellDay;
	}

	public int getBuyDay() {
		return buyDay;
	}

	public void setBuyDay(int buyDay) {
		this.buyDay = buyDay;
	}
}