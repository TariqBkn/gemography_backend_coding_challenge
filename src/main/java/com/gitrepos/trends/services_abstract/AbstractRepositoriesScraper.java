package com.gitrepos.trends.services_abstract;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.routines.UrlValidator;

import com.gitrepos.trends.models_abstract.IRepository;
import com.gitrepos.trends.models_impl.DateRange;
/**
 * This class is responsible for getting trending repositories from a web page.
 * It is abstract to allow different implementations
 * @author bkn_tariq
 */
public abstract class AbstractRepositoriesScraper implements IScraper{
	protected String url;
	 
	protected abstract void setUrl();

	// Will contain the actual steps of scraping the web page corresponding to the url.
	protected abstract List<IRepository> extractRepositoriesFromWebPage() throws IOException;
	
	// Exposed to client classes
	public abstract void setDateRange(DateRange dateRange);
	
	// Exposed to client classes
	public List<IRepository> getRepositories() throws IOException {
		validateUrl(url);
		return extractRepositoriesFromWebPage();
	}
	
	private void validateUrl(String url) {
		String[] schemes = {"http","https"}; //removed FTP which is included by default.
		UrlValidator urlValidator = new UrlValidator(schemes);
		if(!urlValidator.isValid(url)) {
			throw new IllegalArgumentException("Invalid URL value");
		}
	}

	public abstract long countRepositoriesUsingLanguage(String language) throws IOException;
	
	public abstract void setDateRangeFromString(String since);

	public abstract List<String> listLanguagesOfTrendingRepos() throws IOException;

	public abstract List<IRepository> getRepositoriesUsingLanguage(String language) throws IOException;

	public abstract Map<String, Long> getLanguagesByNumberOfRepositories() throws IOException;

	public abstract Map<String, Long> sortLanguagesByRepositories(Map<String, Long> languagesByNumberOfRepositories);


}
