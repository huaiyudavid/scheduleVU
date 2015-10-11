package me.huaiyu.scheduleVU;

import java.util.ArrayList;

/** Defines a particular section of a class */
public class Section {
	/** no identifier fields means classes in the package can directly access them */
	int id;
	int classID;
	int section;
	String professor;
	String days;
	int startTime;
	int endTime;
	String locations;
	
	public Section(String id, String classID, String section, String professor, String days, String startTime,
			String endTime, String locations) {
		this.id = Integer.parseInt(id);
		this.classID = Integer.parseInt(id);
		this.section = Integer.parseInt(section);
		this.professor = professor;
		this.days = days;
		this.startTime = Integer.parseInt(startTime);
		this.endTime = Integer.parseInt(endTime);
		this.locations = locations;
	}
	
	/** Converts startTime and endTime to a readable format ##:## am - ##:## am */
	public String getTimeString() {
		return getStartTime().toString() + " - " + getEndTime().toString();
	}
	
	/** get the startTime in a pretty format ##:## am */
	public Time getStartTime() {
		return new Time(startTime);
	}
	
	/** get the endTime in a pretty format ##:## am */
	public Time getEndTime() {
		return new Time(endTime);
	}
	
	/** Returns an ArrayList of TimeRanges that can be checked to see if this section overlaps with other sections */
	public ArrayList<TimeRange> getTimeRanges() {
		ArrayList<TimeRange> timeRanges = new ArrayList<TimeRange>(days.length());
		for (char day : days.toCharArray()) {
			timeRanges.add(new TimeRange(startTime, endTime, day, day));
		}
		return timeRanges;
	}
	
	public String toString() {
		String result = "";
		result += ("id: " + id + '\n');
		result += ("classID: " + classID + '\n');
		result += ("section: " + section + '\n');
		result += ("professor: " + professor + '\n');
		result += ("days: " + days + '\n');
		result += ("time: " + getTimeString() + '\n');
		result += ("locations: " + locations + '\n');
		return result;
	}
}
