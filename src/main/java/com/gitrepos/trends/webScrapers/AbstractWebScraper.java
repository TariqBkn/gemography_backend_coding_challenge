package com.gitrepos.trends.webScrapers;

import java.io.IOException;
import java.util.List;

import org.apache.commons.validator.routines.UrlValidator;

import com.gitrepos.trends.IModels.IRepository;
/**
 * This class is responsible for getting trending repositories from a web page.
 * @author bkn_tariq
 */
public abstract class AbstractWebScraper {
	protected String url;
	 
	protected abstract void setUrl();

	// Will contain the actual steps of scraping the web page corresponding to the url.
	protected abstract List<IRepository> extractRepositoriesFromWebPage() throws IOException;
	
	// Exposed to client classes
	public List<IRepository> getRepositories() throws IOException {
		validateUrl(url);
		return extractRepositoriesFromWebPage();
	}
	
	public abstract void setDateRange(DateRange dateRange);
	
	private void validateUrl(String url) {
		String[] schemes = {"http","https"}; //removed FTP which is included by default.
		UrlValidator urlValidator = new UrlValidator(schemes);
		if(!urlValidator.isValid(url)) {
			throw new IllegalArgumentException("Invalid URL value");
		}
	}

}
