package me.huaiyu.mysql;

import java.util.ArrayList;

import me.huaiyu.scheduleVU.Class;
import me.huaiyu.scheduleVU.Section;

public class TestDriver {
	public static void main(String[] args) {
		MySQLWrapper database = new MySQLWrapper();
		ArrayList<Class> classes = database.getClasses();
		ArrayList<Section> sections = database.getSections();
		for (Class pika : classes) {
			//System.out.println(pika);
		}
		for (Section chu : sections) {
			System.out.println(chu);
		}
	}
}
