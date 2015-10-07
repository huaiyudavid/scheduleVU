package me.huaiyu.crawling;

import java.io.File;

import com.crawljax.browser.EmbeddedBrowser.BrowserType;
import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.BrowserConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;
import com.crawljax.plugins.crawloverview.CrawlOverview;

public class ClassCrawler {
	public static void main(String[] args) {
		String url = "https://webapp.mis.vanderbilt.edu/more/SearchClasses!input.action";
		String outputDir = "test2";
		ClassCrawler bot = new ClassCrawler(url, outputDir);
		bot.crawl();
		//System.exit(0);
	}
	
	CrawljaxRunner crawljax;
	
	public ClassCrawler(String url, String outputDir) {
		CrawljaxConfigurationBuilder builder = CrawljaxConfiguration.builderFor(url);
		builder.setBrowserConfig(new BrowserConfiguration(BrowserType.CHROME, 1));
		builder.setOutputDirectory(new File(outputDir));
		builder.addPlugin(new CrawlOverview());
		builder.crawlRules().insertRandomDataInInputForms(false);
		builder.crawlRules().clickElementsInRandomOrder(true);
		
		builder.crawlRules().click("a").underXPath("//DIV[@id='advancedSearchLink']");
		builder.crawlRules().click("button").underXPath("//DIV[@id='subjAreaButton-button']");
		builder.crawlRules().click("input").underXPath("//DIV[@id='subjAreaMultiSelectOption145']");
		builder.crawlRules().click("button").underXPath("//DIV[@id='advancedSearchClassesSubmit-button']");


		crawljax = new CrawljaxRunner(builder.build());
	}
	
	public void crawl() {
		crawljax.call();
	}
}
