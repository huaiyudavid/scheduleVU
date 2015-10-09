package me.huaiyu.crawling;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import me.huaiyu.mysql.MySQLWrapper;

public class OfflineCrawler {
	public static void main(String[] args) {
		OfflineCrawler spider = new OfflineCrawler(new File("/crawl/"));
	}

	MySQLWrapper database = new MySQLWrapper();
	ArrayList<File> pages = new ArrayList<File>();

	public OfflineCrawler(File rootDir) {
		findFiles(rootDir);
	}

	public void findFiles(File rootDir) {
		File[] files = rootDir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				pages.add(file);
			} else if (file.isDirectory()) {
				findFiles(file);
			}
		}
	}
	
	public void crawl() {
		for (File page : pages) {
			try {
				parseDocument(page);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void parseDocument(File input) throws IOException {
		Document doc = Jsoup.parse(input, "UTF-8", "http://crawling.huaiyu.me/");
		Element content = doc.getElementById("addClassSectionToCart_div");
		Elements classes = content.getElementsByClass("classTable");
		String name = "";
		String sectionNum = "";
		String hours = "";	//Ex: "3.0 hrs"
		String type = "";
		String days = "";	//Ex: "MWF"
		for (Element classTable : classes) {
			String classAbbrev = classTable.getElementsByClass("classAbbreviation").first().text();
			String classNameDesc = classTable.getElementsByClass("classDescription").first().text();
			name = classAbbrev + " " + classNameDesc;
			Elements oddSections = classTable.getElementsByClass("odd classRow");
			Elements evenSections = classTable.getElementsByClass("even classRow");
			for (Element section : oddSections) {
				sectionNum = section.getElementsByClass("classSection").first().text();
				hours = section.getElementsByClass("classHours").first().text();
				type = section.getElementsByClass("classType").first().text();
				days = section.getElementsByClass("classMeetingDays").first().text();
			}
			for (Element section: evenSections) {
				
			}
		}
	}
}
