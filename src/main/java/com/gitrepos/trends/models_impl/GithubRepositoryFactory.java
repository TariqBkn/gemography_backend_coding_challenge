package com.gitrepos.trends.models_impl;

import org.springframework.stereotype.Component;

import com.gitrepos.trends.models_abstract.IRepository;
import com.gitrepos.trends.models_abstract.IRepositoryFactory;

@Component
public class GithubRepositoryFactory implements IRepositoryFactory{

	@Override
	public IRepository create(String repositoryName, String description, String programmingLanguage,
			String totalStarsNumber, String starsNumberDuringSelectedDateRange) {
		//strip strings
		repositoryName=repositoryName.strip();
		description=description.isBlank()?"No description":description.strip();
		programmingLanguage=programmingLanguage.strip();
		totalStarsNumber=totalStarsNumber.strip();
		starsNumberDuringSelectedDateRange=starsNumberDuringSelectedDateRange.replace("stars", "").replace("today", "").replace("this","").replace("week","").replace("month","").strip();
		
		// On Github a thousand stars is written like : 1,000, it should become 1000
		int totalStarsNumberValue=Integer.parseInt(totalStarsNumber.replace(",", ""));
		int starsNumberDuringSelectedDateRangeValue=Integer.parseInt(starsNumberDuringSelectedDateRange.replace(",", ""));
		
		//This repository is not related to a programming language, it is treated a PlainTextRepository
		if(programmingLanguage.equals("")) return new GithubPlainTextRepository(repositoryName, description,
			totalStarsNumberValue, starsNumberDuringSelectedDateRangeValue);
		
		return new GithubCodeRepository(repositoryName, description,programmingLanguage,
						totalStarsNumberValue, starsNumberDuringSelectedDateRangeValue);
	}

	
}
