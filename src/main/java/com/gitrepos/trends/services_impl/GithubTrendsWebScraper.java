package com.gitrepos.trends.services_impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gitrepos.trends.models_abstract.ICodeRepository;
import com.gitrepos.trends.models_abstract.IRepository;
import com.gitrepos.trends.models_abstract.IRepositoryFactory;
import com.gitrepos.trends.models_impl.DateRange;
import com.gitrepos.trends.services_abstract.AbstractRepositoriesScraper;

@Service
public class GithubTrendsWebScraper extends AbstractRepositoriesScraper {
	
	private static final String GITHUB_URL="https://github.com/trending";
	private DateRange dateRange=DateRange.DAILY;
	@Autowired
	IRepositoryFactory githubRepositoryFactory;
	
	@Override
	protected void setUrl() {
		url=GITHUB_URL+"?since="+dateRange;
	}
	
	@Override
	public void setDateRange(DateRange dateRange) {
		this.dateRange=dateRange;
		//Update Url each time date range changes
		setUrl();
	}

	@Override
	 protected List<IRepository> extractRepositoriesFromWebPage() throws IOException {
		// This is the "Trending Repositories" web page on Github
		final Document githubTrendingPage = Jsoup.connect(url).get();
		
		List<IRepository> extractedRepositories = new ArrayList<>();
		
		for (Element row : githubTrendingPage.select("article")) {
			String repositoryTitle = row.select(".lh-condensed.h3").text();
			String description = row.select(".pr-4.my-1.text-gray.col-9").text();
			String starsTodayString = row.select(".float-sm-right.d-inline-block").text();
			String programmingLanguage = row.select(".mr-3.ml-0.d-inline-block").text();
			String totalStarsNumberString = row.select("a.mr-3.d-inline-block.muted-link:nth-of-type(1)").text();
		
			extractedRepositories.add(githubRepositoryFactory.create(repositoryTitle,description,programmingLanguage,totalStarsNumberString, starsTodayString));	
		}
		return extractedRepositories;
	}
	
	 public List<String> listLanguagesOfTrendingRepos() throws IOException {
		  return streamCodeRepositoriesFromWebScraper(this)
				.map(ICodeRepository::getProgrammingLanguage)
				.distinct()
				.collect(Collectors.toList());		
	 }
	
	 public long countRepositoriesUsingLanguage(String language) throws IOException {
		 return     	streamCodeRepositoriesFromWebScraper(this)
						.filter(repo -> repo.getProgrammingLanguage().equalsIgnoreCase(language) )
						.count();
	 }
	 public Map<String, Long> sortLanguagesByRepositories(Map<String, Long> languagesByNumberOfRepositories) {
	 	return languagesByNumberOfRepositories.entrySet()
					        .stream()
					        .sorted(Collections.reverseOrder(Entry.comparingByValue()))
					        .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1,  e2) -> e1, LinkedHashMap::new));
	 }

	 public  Map<String, Long> getLanguagesByNumberOfRepositories() throws IOException {
		return streamCodeRepositoriesFromWebScraper(this)
		.collect(Collectors.groupingBy(ICodeRepository::getProgrammingLanguage, Collectors.counting()));	         
	 }
	 
	 public Stream<ICodeRepository> streamCodeRepositoriesFromWebScraper(AbstractRepositoriesScraper webScraper) throws IOException {
	    return   webScraper
	    		.getRepositories()
	    		.stream()
	    		.filter(repo->repo instanceof ICodeRepository) // Eliminate repositories containing plain text only
				.map(ICodeRepository.class::cast);			
	}
	 
	public List<IRepository> getRepositoriesUsingLanguage(String language) throws IOException {
		 return      	 streamCodeRepositoriesFromWebScraper(this)
						.filter(repo -> repo.getProgrammingLanguage().equalsIgnoreCase(language))
						.collect(Collectors.toList()); 
	}
	
	public void setDateRangeFromString(String since) {
	 	if(since.equalsIgnoreCase("weekly")) {
			this.setDateRange(DateRange.WEEKLY);
			return;
		}
	 	if(since.equalsIgnoreCase("monthly")) {
			this.setDateRange(DateRange.MONTHLY);
			return;
		}
	 	// Any other value will set DateRange to daily
		this.setDateRange(DateRange.DAILY);
	}
	 
	
}
