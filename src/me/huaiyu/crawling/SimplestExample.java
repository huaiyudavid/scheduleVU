package me.huaiyu.crawling;

import java.io.File;

import com.crawljax.browser.EmbeddedBrowser.BrowserType;
import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.BrowserConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;
import com.crawljax.plugins.crawloverview.CrawlOverview;

/**
 * Crawls our demo site with the default configuration. The crawl will log what it's doing but will
 * not produce any output.
 */
public class SimplestExample {

	/**
	 * Run this method to start the crawl.
	 */
	public static void main(String[] args) {
		CrawljaxConfigurationBuilder builder = CrawljaxConfiguration.builderFor("https://webapp.mis.vanderbilt.edu/more/SearchClasses!input.action?commodoreIdToLoad=C04220215");
		builder.setBrowserConfig(new BrowserConfiguration(BrowserType.CHROME, 2));
		builder.setOutputDirectory(new File("test"));
		builder.addPlugin(new CrawlOverview());
		CrawljaxRunner crawljax =
		        new CrawljaxRunner(builder.build());
		crawljax.call();
	}
}