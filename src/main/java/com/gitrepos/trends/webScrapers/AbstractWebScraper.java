package com.gitrepos.trends.webScrapers;

import java.util.List;

import org.apache.commons.validator.routines.UrlValidator;

import com.gitrepos.trends.models.IRepository;
/**
 * This class is responsible for getting trending repositories from a web page.
 * @author bkn_tariq
 */
public abstract class AbstractWebScraper {
	private String url;
	public AbstractWebScraper(String url) {
		validateTargetUrl(url);
		this.url=url;
	} 

	// Will contain the actual steps of scraping the web page corresponding 
	// to the url.
	protected abstract List<IRepository> getRepositoriesFromWebPage();
	
	private void validateTargetUrl(String url) {
		String[] schemes = {"http","https"}; //removed FTP which is included by default.
		UrlValidator urlValidator = new UrlValidator(schemes);
		if(!urlValidator.isValid(url)) {
			throw new IllegalArgumentException("Invalid URL value");
		}
	}
}
