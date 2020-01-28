package com.gitrepos.trends.controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gitrepos.trends.IModels.ICodeRepository;
import com.gitrepos.trends.webScrapers.AbstractWebScraper;
import com.gitrepos.trends.webScrapers.DateRange;

@RestController
@RequestMapping(value="/repositories/trending")
public class TrendingRepositoriesController {
	
	private final AbstractWebScraper webScraper;
	
	TrendingRepositoriesController(@Autowired AbstractWebScraper webScraper) {
		this.webScraper=webScraper;
	}
	
	//list the languages used by the trending repositories
	@GetMapping(value = "/languages")
	public ResponseEntity<List<String>> listLanguesOfTrendingRepositories(@RequestParam(defaultValue="") String since) {
		HttpHeaders headers = new HttpHeaders();
		try {
			setDateRange(since);
			
			List<String> languages = listLanguagesOfTrendingRepos();
		
	        headers.add("Message", "List of languages in trending repositories");
	        
	        return ResponseEntity.accepted().headers(headers).body(languages);
		} catch (IOException e) {
			headers.add("Message", "ERROR: can't get list of languages.");
	        return ResponseEntity.accepted().headers(headers).body(null);
		}
		
	}

	private List<String> listLanguagesOfTrendingRepos() throws IOException {
		List<String> languages = webScraper
								.getRepositories()
								.stream()
								.filter(repo->repo instanceof ICodeRepository)
								.map(ICodeRepository.class::cast)
								.map(repo->repo.getProgrammingLanguage())
								.distinct()
								.collect(Collectors.toList());		
		return languages;
	}
	
		
	private void setDateRange(String since) {
		if(since.equalsIgnoreCase("weekly")) {
			webScraper.setDateRange(DateRange.WEEKLY);
		}else if(since.equalsIgnoreCase("monthly")) {
			webScraper.setDateRange(DateRange.MONTHLY);
		}else {
			webScraper.setDateRange(DateRange.DAILY);
		}
	}
}
