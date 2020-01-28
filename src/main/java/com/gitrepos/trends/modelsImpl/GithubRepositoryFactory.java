package com.gitrepos.trends.modelsImpl;

import org.springframework.stereotype.Component;

import com.gitrepos.trends.IModels.IRepository;
import com.gitrepos.trends.IModels.IRepositoryFactory;

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
		
		// On github a thousand stars is written like : 1,000, it should become 1000
		int totalStarsNumberValue=Integer.valueOf(totalStarsNumber.replace(",", ""));
		int starsNumberDuringSelectedDateRangeValue=Integer.valueOf(starsNumberDuringSelectedDateRange.replace(",", ""));
		
		if(programmingLanguage.isBlank()) {
			return new GithubCodeRepository(repositoryName, description,programmingLanguage,
			totalStarsNumberValue, starsNumberDuringSelectedDateRangeValue);
		}else {
			return new GithubPlainTextRepository(repositoryName, description, programmingLanguage,
				totalStarsNumberValue, starsNumberDuringSelectedDateRangeValue);
		}
	}

	
}
