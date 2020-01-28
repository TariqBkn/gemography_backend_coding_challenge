package com.gitrepos.trends.models_impl;

import org.springframework.stereotype.Component;

import com.gitrepos.trends.abstract_models.IRepository;
import com.gitrepos.trends.abstract_models.IRepositoryFactory;

@Component
public class GithubRepositoryFactory implements IRepositoryFactory{

	@Override
	public IRepository create(String repositoryName, String description, String programmingLanguage,
			String totalStarsNumber, String starsNumberDuringSelectedDateRange) {
		//strip string
		repositoryName=repositoryName.strip();
		description=description.isBlank()?"No description":description.strip();
		programmingLanguage=programmingLanguage.strip();
		totalStarsNumber=totalStarsNumber.strip();
		starsNumberDuringSelectedDateRange=starsNumberDuringSelectedDateRange.replace("stars", "").replace("today", "").replace("this","").replace("week","").replace("month","").strip();
		
		// On Github a thousand stars is written like : 1,000, it should become 1000
		int totalStarsNumberValue=Integer.parseInt(totalStarsNumber.replace(",", ""));
		int starsNumberDuringSelectedDateRangeValue=Integer.parseInt(starsNumberDuringSelectedDateRange.replace(",", ""));
		
		if(programmingLanguage.equals("")) {
			return new GithubPlainTextRepository(repositoryName, description,
			totalStarsNumberValue, starsNumberDuringSelectedDateRangeValue);
		}else {
			return new GithubCodeRepository(repositoryName, description,programmingLanguage,
					totalStarsNumberValue, starsNumberDuringSelectedDateRangeValue);
		}
	}

	
}
