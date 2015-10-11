package me.huaiyu.scheduleVU;

/** Defines a class that Vanderbilt offers */
public class Class {
	/** no identifier fields means classes in the package can directly access them */
	int id;
	String name;
	String description;
	String grading;
	int hours;
	String prereq;
	String coreq;
	
	public Class(String id, String name, String description, String grading, String hours, String prereq,
			String coreq) {
		this.id = Integer.parseInt(id);
		this.name = name;
		this.description = description;
		this.grading = grading;
		this.hours = Integer.parseInt(hours);
		this.prereq = prereq;
		this.coreq = coreq;
	}
	
	/** compares two Classes based on ID */
	public boolean equals(Class other) {
		return this.id == other.id;
	}
	
	public String toString() {
		String result = "";
		result += ("id: " + id + '\n');
		result += ("name: " + name + '\n');
		result += ("description: " + description + '\n');
		result += ("grading: " + grading + '\n');
		result += ("hours: " + hours + '\n');
		result += ("prereq: " + prereq + '\n');
		result += ("coreq: " + coreq + '\n');
		return result;
	}
}
