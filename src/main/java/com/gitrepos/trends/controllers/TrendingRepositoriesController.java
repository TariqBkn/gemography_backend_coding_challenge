package com.gitrepos.trends.controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gitrepos.trends.abstract_models.ICodeRepository;
import com.gitrepos.trends.abstract_models.IRepository;
import com.gitrepos.trends.web_scrapers.AbstractWebScraper;
import com.gitrepos.trends.web_scrapers.DateRange;

@RestController
@RequestMapping(value="/repositories/trending")
public class TrendingRepositoriesController {
	
	private static final String MESSAGE_HEADER_NAME = "Message";
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
		
	        headers.add(MESSAGE_HEADER_NAME, "List of languages in trending repositories");
	        
	        return ResponseEntity.accepted().headers(headers).body(languages);
		} catch (IOException e) {
			headers.add(MESSAGE_HEADER_NAME, "ERROR: can't get list of languages.");
	        return ResponseEntity.accepted().headers(headers).body(null);
		}
		
	}

	private List<String> listLanguagesOfTrendingRepos() throws IOException {
	  return streamCodeRepositoriesFromWebScraper(webScraper)
			.map(ICodeRepository::getProgrammingLanguage)
			.distinct()
			.collect(Collectors.toList());		
	}
	 
		// Get the number of repositories using a given language
		@GetMapping(value = "/languages/{language}/count")
		public ResponseEntity<Integer> countRepositoriesUsingLanguage(@RequestParam(defaultValue="") String since, @PathVariable String language) {
			HttpHeaders headers = new HttpHeaders();
			try {
				setDateRange(since);
				int numberOfReposUsingLanguage = countRepositoriesUsingLanguage(language);
				
		        headers.add(MESSAGE_HEADER_NAME, "Number of repositories using the specified language");
		        
		        return ResponseEntity.accepted().headers(headers).body(numberOfReposUsingLanguage);
			} catch (IOException e) {
				headers.add(MESSAGE_HEADER_NAME, "ERROR: can't get the number of repositories using the specified language");
		        return ResponseEntity.accepted().headers(headers).body(null);
			}
		}

		private int countRepositoriesUsingLanguage(String language) throws IOException {
			return (int)     streamCodeRepositoriesFromWebScraper(webScraper)
							.filter(repo -> repo.getProgrammingLanguage().equalsIgnoreCase(language) )
							.count();
		}
	
		// List repositories using a given language
		@GetMapping(value = "/languages/{language}")
		public ResponseEntity<List<IRepository>> listRepositoriesUsingLanguage(@RequestParam(defaultValue="") String since, @PathVariable String language) {
				HttpHeaders headers = new HttpHeaders();
				try {
					setDateRange(since);
					List<IRepository> repositoriesUsingLanguage = getRepositoriesUsingLanguage(language);
					
			        headers.add(MESSAGE_HEADER_NAME, "Repositories using given language");
			    
			        return ResponseEntity.accepted().headers(headers).body(repositoriesUsingLanguage);
				} catch (IOException e) {
					headers.add(MESSAGE_HEADER_NAME, "ERROR: can't the list of repositories using specified language");
			        return ResponseEntity.accepted().headers(headers).body(null);
				}
			}

		private List<IRepository> getRepositoriesUsingLanguage(String language) throws IOException {
		 return      	 streamCodeRepositoriesFromWebScraper(webScraper)
						.filter(repo -> repo.getProgrammingLanguage().equalsIgnoreCase(language))
						.collect(Collectors.toList()); 
		}
			

		// Get Language popularity over the 25 trending repositories
		@GetMapping(value = "/languages/popularity")
		public ResponseEntity<Map<String, Long>> getLanguagePopularity(@RequestParam(defaultValue="") String since) {
			HttpHeaders headers = new HttpHeaders();
			try {
				setDateRange(since);
				Map<String, Long> laguagesByRank = sortLanguagesByRepositories(getLanguagesByNumberOfRepositories());

		        headers.add(MESSAGE_HEADER_NAME, "Language popularity");
		        
		        return ResponseEntity.accepted().headers(headers).body(laguagesByRank);
			} catch (IOException e) {
				headers.add(MESSAGE_HEADER_NAME, "ERROR: can't get the list of repositories using specified language");
		        return ResponseEntity.accepted().headers(headers).body(null);
			}
		}

	 private Map<String, Long> sortLanguagesByRepositories(Map<String, Long> languagesByNumberOfRepositories) {
	 	return languagesByNumberOfRepositories.entrySet()
					        .stream()
					        .sorted(Collections.reverseOrder(Entry.comparingByValue()))
					        .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1,  e2) -> e1, LinkedHashMap::new));
	 }

	 private  Map<String, Long> getLanguagesByNumberOfRepositories() throws IOException {
		return 					 streamCodeRepositoriesFromWebScraper(webScraper)
								.collect(Collectors.groupingBy(ICodeRepository::getProgrammingLanguage, Collectors.counting()));	         
	 }
	 
	 private Stream<ICodeRepository> streamCodeRepositoriesFromWebScraper(AbstractWebScraper webScraper) throws IOException {
	    return   webScraper
	    		.getRepositories()
	    		.stream()
	    		.filter(repo->repo instanceof ICodeRepository)
				.map(ICodeRepository.class::cast);			
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
