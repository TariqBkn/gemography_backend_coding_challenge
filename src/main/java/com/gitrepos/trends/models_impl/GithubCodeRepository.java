package com.gitrepos.trends.models_impl;

import com.gitrepos.trends.abstract_models.ICodeRepository;

// Contains code and plain Text
public class GithubCodeRepository extends GithubPlainTextRepository implements ICodeRepository {
	protected final String programmingLanguage;
	
	public GithubCodeRepository(String repositoryName, String description, String programmingLanguage, int totalStarsNumber,
			int starsNumberDuringSelectedDateRange) {
		super(repositoryName, description, totalStarsNumber, starsNumberDuringSelectedDateRange);
		this.programmingLanguage=programmingLanguage;
	}

	@Override
	public String getProgrammingLanguage() {
		return programmingLanguage;
	}

}
