package me.huaiyu.crawling;

import java.io.File;
import java.util.concurrent.TimeUnit;

import com.crawljax.browser.EmbeddedBrowser.BrowserType;
import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.BrowserConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;
import com.crawljax.plugins.crawloverview.CrawlOverview;

public class ClassCrawler {
	public static void main(String[] args) {
		String url = "https://webapp.mis.vanderbilt.edu/more/SearchClasses!input.action";
		String outputDir = "test";
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
		builder.crawlRules().clickElementsInRandomOrder(false);
		builder.crawlRules().crawlHiddenAnchors(true);
		builder.crawlRules().waitAfterEvent(10000, TimeUnit.MILLISECONDS);
		
		/*InputSpecification input = new InputSpecification();
		Form inputForm = new Form();
		inputForm.field("searchClassSectionsInput").setValue("es 1401");
		input.setValuesInForm(inputForm).beforeClickElement("button").withAttribute("title", "Search Classes");
		builder.crawlRules().setInputSpec(input);*/
		
		//builder.crawlRules().click("input").withAttribute("id", "searchClassSectionsInput");
		//builder.crawlRules().click("button").withAttribute("title", "Search Classes");
		builder.crawlRules().click("a").withAttribute("id", "advancedSearchLink");
		builder.crawlRules().click("input").withAttribute("title", "Mathematics");
		builder.crawlRules().click("button").withAttribute("id", "advancedSearchClassesSubmit-button");
		builder.crawlRules().clickOnce(false);
		
		//builder.crawlRules().click("a").withAttribute("id", "advancedSearchLink");
		//builder.crawlRules().click("input").withAttribute("id", "advancedSearchForm_searchCriteria_classStatusCodes");
		//builder.crawlRules().click("span").withAttribute("id", "subjAreaButton");
		//builder.crawlRules().click("input").withAttribute("id", "subjAreaMultiSelectOption145");
		//builder.crawlRules().click("button").withAttribute("id", "advancedSearchClassesSubmit-button");

		crawljax = new CrawljaxRunner(builder.build());
	}
	
	public void crawl() {
		crawljax.call();
	}
}
