package com.gitrepos.trends.web_scrapers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gitrepos.trends.abstract_models.IRepository;
import com.gitrepos.trends.abstract_models.IRepositoryFactory;

@Component
public class GithubTrendsWebScraper extends AbstractWebScraper {
	
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
			String starsTodayString = row.select(".float-sm-right.d-inline-block").text();//select("aricle").text()
			String programmingLanguage = row.select(".mr-3.ml-0.d-inline-block").text();
			String totalStarsNumberString = row.select("a.mr-3.d-inline-block.muted-link:nth-of-type(1)").text();
	
			
			extractedRepositories.add(githubRepositoryFactory.create(repositoryTitle,description,programmingLanguage,totalStarsNumberString, starsTodayString));	
		}
		return extractedRepositories;
	}

}
