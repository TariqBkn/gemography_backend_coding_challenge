package com.gitrepos.trends.modelsImpl;

import java.util.Optional;

import com.gitrepos.trends.IModels.ICodeRepository;

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
