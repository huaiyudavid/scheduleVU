package me.huaiyu.scheduleVU;

/** Class that stores a time range in military format with day of week included */
public class TimeRange {
	private int start, end;

	public TimeRange(int start, int end, char startDay, char endDay) {
		int startDayNum = dayCharToInt(startDay);
		int endDayNum = dayCharToInt(endDay);
		this.start = startDayNum*10000 + start;
		this.end = endDayNum*10000 + end;
	}
	
	/** Checks to see if two TimeRanges overlap */
	public boolean overlaps(TimeRange other) {
		return start >= other.start && start <= other.end || end >= other.start && end <= other.end;
	}
	
	/** converts this TimeRange to a String (for debugging) */
	public String toString() {
		return "" + start + " - " + end;
	}
	
	/** Converts from 'MTWRF' to '12345' */
	private int dayCharToInt(char day) {
		int result = 0;
		switch (day) {
		case 'M':
			result = 1;
			break;
		case 'T':
			result = 2;
			break;
		case 'W':
			result = 3;
			break;
		case 'R':
			result = 4;
			break;
		case 'F':
			result = 5;
			break;
		default:
			System.out.println("Error: wrong date format");
		}
		return result;
	}
}
