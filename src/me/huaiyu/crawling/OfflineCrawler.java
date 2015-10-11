package me.huaiyu.crawling;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import me.huaiyu.mysql.MySQLWrapper;

public class OfflineCrawler {
	public static void main(String[] args) {
		OfflineCrawler spider = new OfflineCrawler(new File("../scheduleVU/crawl/"));
		spider.crawl();
	}

	MySQLWrapper database = new MySQLWrapper();
	ArrayList<File> pages = new ArrayList<File>();
	long sectionID = 0;
	long classID = 0;

	public OfflineCrawler(File rootDir) {
		findFiles(rootDir);
	}

	public void findFiles(File rootDir) {
		File[] files = rootDir.listFiles();
		//System.out.println(rootDir.getAbsolutePath());
		for (File file : files) {
			if (file.isFile() && file.getName().contains(".html")) {
				pages.add(file);
			} else if (file.isDirectory()) {
				findFiles(file);
			}
		}
	}

	public void crawl() {
		for (File page : pages) {
			try {
				//System.out.println("hi");
				parseDocument(page);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void parseDocument(File input) throws IOException {
		Document doc = Jsoup.parse(input, "UTF-8", "http://crawling.huaiyu.me/");
		Element content = doc.getElementById("addClassSectionToCart_div");
		// System.out.println(input);
		Elements classes = content.getElementsByClass("classTable");
		String name = "";
		String sectionNum = "";
		String hours = ""; // Ex: "3.0 hrs"
		String type = "";
		String days = ""; // Ex: "MWF"
		String time = ""; // Ex: "02:10p - 03:00p" "10:10a - 11:00a"
		String location = ""; // "Featheringill Hall 134"
		String professor = ""; // Ex: "Tairas, Robert A."
		for (Element classTable : classes) {
			String classAbbrev = classTable.getElementsByClass("classAbbreviation").first().text();
			String classNameDesc = classTable.getElementsByClass("classDescription").first().text();
			name = classAbbrev + " " + classNameDesc;
			Elements sections = classTable.getElementsByClass("classRow");
			// Elements evenSections = classTable.getElementsByClass("even
			// classRow");
			// System.out.println(sections.size());
			for (Element section : sections) {
				sectionNum = section.getElementsByClass("classSection").first().text();
				hours = section.getElementsByClass("classHours").first().text();
				type = section.getElementsByClass("classType").first().text();
				days = section.getElementsByClass("classMeetingDays").first().text();
				time = section.getElementsByClass("classMeetingTimes").first().text();
				location = section.getElementsByClass("classBuilding").first().text();
				professor = section.getElementsByClass("classInstructor").first().text();
				output(name, sectionNum, hours, type, days, time, location, professor);
			}
			/*
			 * for (Element section : evenSections) { sectionNum =
			 * section.getElementsByClass("classSection").first().text(); hours
			 * = section.getElementsByClass("classHours").first().text(); type =
			 * section.getElementsByClass("classType").first().text(); days =
			 * section.getElementsByClass("classMeetingDays").first().text();
			 * time =
			 * section.getElementsByClass("classMeetingTimes").first().text();
			 * professor =
			 * section.getElementsByClass("classInstructor").first().text();
			 * output(name, sectionNum, hours, type, days, time, location,
			 * professor); }
			 */
		}
	}

	private void output(String name, String sectionNum, String hours, String type, String days, String time,
			String location, String professor) {
		if (hours.length() > 2) {
			hours = hours.substring(0, hours.indexOf("."));
		} else {
			hours = "0";
		}

		if (type.equals("Discussion")) {
			name = name + " " + type;
			int sectionNumInt = Integer.parseInt(sectionNum) - 1;
			sectionNum = "" + sectionNumInt;
		}
		
		String[] times = parseTime(time);

		/*
		 * try { System.out.println("bye"); PrintWriter out = new
		 * PrintWriter(new BufferedWriter(new FileWriter("output.txt", true)));
		 * out.println("name: " + name); out.println("sectionNum: " +
		 * sectionNum); out.println("hours: " + hours); out.println("type: " +
		 * type); out.println("days: " + days); String[] times =
		 * parseTime(time); String start = times[0]; String end = times[1];
		 * out.println("time: " + start + " " + end); out.println("location: " +
		 * location); out.println("professor: " + professor); out.println();
		 * out.close(); } catch (IOException ex) { ex.printStackTrace(); }
		 */
		if (!days.contains("TBA") && !time.contains("TBA") && !location.contains("TBA")) {
			database.addSection("" + sectionID, "" + classID, sectionNum, professor, days, times[0], times[1], location);
			sectionID++;
			if (Integer.parseInt(sectionNum) == 1) {
				database.addClass("" + classID, name, "insert description here", "grading policy", hours, "", "");
				classID++;
			}
		}
	}

	// returns a String array with [startTime, endTime] given a time in format:
	// ab:cd - ef:gh
	private String[] parseTime(String time) {
		System.out.println(time);
		String startTime = "";
		String endTime = "";
		if (time.length() > 5) {
			int indexOfColonl = time.indexOf(":");
			int indexOfColonr = time.lastIndexOf(":");
			startTime = time.substring(0, indexOfColonl) + time.substring(indexOfColonl + 1, indexOfColonl + 3);
			endTime = time.substring(indexOfColonr - 2, indexOfColonr)
					+ time.substring(indexOfColonr + 1, indexOfColonr + 3);
			char startMod = time.charAt(indexOfColonl + 3);
			char endMod = time.charAt(indexOfColonr + 3);
			if (startMod == 'p') {
				int startTimeInt = (Integer.parseInt(startTime) + 1200);
				if (startTimeInt < 2400) {
					startTime = "" + startTimeInt;
				}
			} else {
				int startTimeInt = (Integer.parseInt(startTime) - 1200);
				if (startTimeInt >= 0) {
					startTime = "" + startTimeInt;
				}
			}
			if (endMod == 'p') {
				int endTimeInt = (Integer.parseInt(endTime) + 1200);
				if (endTimeInt < 2400) {
					endTime = "" + endTimeInt;
				}
			} else {
				int endTimeInt = (Integer.parseInt(endTime) - 1200);
				if (endTimeInt >= 0) {
					endTime = "" + endTimeInt;
				}
			}
		}
		return new String[] { startTime, endTime };
	}
}
