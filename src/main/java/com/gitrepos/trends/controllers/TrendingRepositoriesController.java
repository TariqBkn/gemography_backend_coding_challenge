package com.gitrepos.trends.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gitrepos.trends.models_abstract.IRepository;
import com.gitrepos.trends.services_abstract.AbstractRepositoriesScraper;

@RestController
@RequestMapping(value="api/repositories/trending")
public class TrendingRepositoriesController {
	
	private static final String MESSAGE_HEADER_NAME = "Message";
	private final AbstractRepositoriesScraper webScraper;
	
	TrendingRepositoriesController(@Autowired AbstractRepositoriesScraper webScraper) {
		this.webScraper=webScraper;
	}
	
	//list the languages used by the trending repositories
	@GetMapping(value = "/languages")
	public ResponseEntity<List<String>> listLanguesOfTrendingRepositories(@RequestParam(defaultValue="") String since) {
		HttpHeaders headers = new HttpHeaders();
		try {
			webScraper.setDateRangeFromString(since);
			
			List<String> languages = webScraper.listLanguagesOfTrendingRepos();
		
	        headers.add(MESSAGE_HEADER_NAME, "List of languages in trending repositories");
	        
	        return ResponseEntity.accepted().headers(headers).body(languages);
		} catch (IOException e) {
			headers.add(MESSAGE_HEADER_NAME, "ERROR: can't get list of languages.");
	        return ResponseEntity.noContent().build();
		}
	}
	 
	// Get the number of repositories using a given language
	@GetMapping(value = "/languages/{language}/count")
	public ResponseEntity<Long> countRepositoriesUsingLanguage(@RequestParam(defaultValue="") String since, @PathVariable String language) {
		HttpHeaders headers = new HttpHeaders();
		try {
			webScraper.setDateRangeFromString(since);
			long numberOfReposUsingLanguage = webScraper.countRepositoriesUsingLanguage(language);
			
	        headers.add(MESSAGE_HEADER_NAME, "Number of repositories using the specified language");
	        
	        return ResponseEntity.accepted().headers(headers).body(numberOfReposUsingLanguage);
		} catch (IOException e) {
			headers.add(MESSAGE_HEADER_NAME, "ERROR: can't get the number of repositories using the specified language");
	        return ResponseEntity.accepted().headers(headers).body(null);
		}
	}
	
	// List repositories using a given language
	@GetMapping(value = "/languages/{language}")
	public ResponseEntity<List<IRepository>> listRepositoriesUsingLanguage(@RequestParam(defaultValue="") String since, @PathVariable String language) {
		HttpHeaders headers = new HttpHeaders();
		try {
			webScraper.setDateRangeFromString(since);
			List<IRepository> repositoriesUsingLanguage = webScraper.getRepositoriesUsingLanguage(language);
			
	        headers.add(MESSAGE_HEADER_NAME, "Repositories using given language");
	    
	        return ResponseEntity.accepted().headers(headers).body(repositoriesUsingLanguage);
		} catch (IOException e) {
			headers.add(MESSAGE_HEADER_NAME, "ERROR: can't the list of repositories using specified language");
	        return ResponseEntity.accepted().headers(headers).body(null);
		}
	}

	// Get Language popularity over the 25 trending repositories
	@GetMapping(value = "/languages/popularity")
	public ResponseEntity<Map<String, Long>> getLanguagePopularity(@RequestParam(defaultValue="") String since) {
		HttpHeaders headers = new HttpHeaders();
		try {
			webScraper.setDateRangeFromString(since);
			Map<String, Long> laguagesByRank = webScraper.sortLanguagesByRepositories(webScraper.getLanguagesByNumberOfRepositories());

	        headers.add(MESSAGE_HEADER_NAME, "Language popularity");
	        
	        return ResponseEntity.accepted().headers(headers).body(laguagesByRank);
		} catch (IOException e) {
			headers.add(MESSAGE_HEADER_NAME, "ERROR: can't get the list of repositories using specified language");
	        return ResponseEntity.accepted().headers(headers).body(null);
		}
	}
	 
	 
	
}
