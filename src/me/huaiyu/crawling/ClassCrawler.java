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
		String url = "http://demo.crawljax.com/";
		String outputDir = "test3";
		ClassCrawler bot = new ClassCrawler(url, outputDir);
		bot.crawl();
		//System.exit(0);
	}
	
	CrawljaxRunner crawljax;
	
	public ClassCrawler(String url, String outputDir) {
		CrawljaxConfigurationBuilder builder = CrawljaxConfiguration.builderFor(url);
		builder.setBrowserConfig(new BrowserConfiguration(BrowserType.CHROME, 2));
		builder.setOutputDirectory(new File(outputDir));
		builder.addPlugin(new CrawlOverview());

		crawljax = new CrawljaxRunner(builder.build());
	}
	
	public void crawl() {
		crawljax.call();
	}
}
