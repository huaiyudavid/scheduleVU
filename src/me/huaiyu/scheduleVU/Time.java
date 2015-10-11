package me.huaiyu.scheduleVU;

/** stores a Time in military format; used for displaying Times in a pretty format */
public class Time {
	int time;
	
	public Time(int time) {
		this.time = time;
	}
	
	/** Displays Time in normal format ##:## am */
	public String toString() {
		int hour = time / 100;
		String period = "am";
		if (hour > 12) {
			hour -= 12;
			period = "pm";
		} else if (hour == 12) {
			period = "pm";
		} else if (hour == 0) {
			hour += 12;
		}
		int min = time % 100;
		return String.format("%02d:%02d %s", hour, min, period);
	}
}
