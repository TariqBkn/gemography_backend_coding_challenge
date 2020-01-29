package com.gitrepos.trends.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gitrepos.trends.models_abstract.IRepository;
import com.gitrepos.trends.reponse_entities.ResponseBody;
import com.gitrepos.trends.services_abstract.AbstractRepositoriesScraper;

@RestController
@RequestMapping(value="api/v1/repositories/trending")
public class TrendingRepositoriesController {
	
	private final AbstractRepositoriesScraper webScraper;
	TrendingRepositoriesController(@Autowired AbstractRepositoriesScraper webScraper) {
		this.webScraper=webScraper;
	}
	
	//list the languages used by the trending repositories
	@GetMapping(value = "/languages")
	public ResponseEntity<ResponseBody> listLanguesOfTrendingRepositories(@RequestParam(defaultValue="") String since) {
		try {
			webScraper.setDateRangeFromString(since);
			
			List<String> languages = webScraper.listLanguagesOfTrendingRepos();
	
	        return ResponseEntity
	        		.ok()
	        		.body(
	        			new ResponseBody("List of languages used by the trending repositories", languages)
	        			);
		} catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} 
	}
	 
	// Get the number of repositories using a given language
	@GetMapping(value = "/languages/{language}/count")
	public ResponseEntity<ResponseBody> countRepositoriesUsingLanguage(@RequestParam(defaultValue="") String since, @PathVariable String language) {
 		try {
			webScraper.setDateRangeFromString(since);
			long numberOfReposUsingLanguage = webScraper.countRepositoriesUsingLanguage(language);
			
	        return ResponseEntity.ok().body(
	        							   new ResponseBody("Number of repositories using "+language, numberOfReposUsingLanguage ) 
	        								);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	// List repositories using a given language
	@GetMapping(value = "/languages/{language}")
	public ResponseEntity<ResponseBody> listRepositoriesUsingLanguage(@RequestParam(defaultValue="") String since, @PathVariable String language) {
		HttpHeaders headers = new HttpHeaders();
		try {
			webScraper.setDateRangeFromString(since);
			List<IRepository> repositoriesUsingLanguage = webScraper.getRepositoriesUsingLanguage(language);
	    
	        return ResponseEntity.ok().headers(headers).body(
	        												new ResponseBody("Repositories using "+language, repositoriesUsingLanguage)
	        												);
		} catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// Get Language popularity over the 25 trending repositories
	@GetMapping(value = "/languages/popularity")
	public ResponseEntity<ResponseBody> getLanguagePopularity(@RequestParam(defaultValue="") String since) {
		try {
			webScraper.setDateRangeFromString(since);
			Map<String, Long> laguagesByRank = webScraper.sortLanguagesByRepositories(webScraper.getLanguagesByNumberOfRepositories());

	        return ResponseEntity.ok().body(
	        							  new ResponseBody("Number of repositories using each language - ranked by popularity ", laguagesByRank)
	        							  );
		} catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	 
	 
	
}
