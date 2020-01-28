package com.gitrepos.trends.modelsImpl;

import com.gitrepos.trends.IModels.IRepository;

// A GitHub repository that contains plain text only
public class GithubPlainTextRepository implements IRepository {
	protected final String repositoryName;
	protected final int totalStarsNumber;
	protected final int starsNumberDuringSelectedDateRange;
	protected final String description;

	public GithubPlainTextRepository(String repositoryName, String description, String programmingLanguage, int totalStarsNumber,
			int starsNumberDuringSelectedDateRange) {
		super();
		this.repositoryName = repositoryName;
		this.description=description;
		this.totalStarsNumber = totalStarsNumber;
		this.starsNumberDuringSelectedDateRange = starsNumberDuringSelectedDateRange;
	}

	@Override
	public String getRepositoryName() {
		return repositoryName;
	}
	
	@Override
	public int getTotalStarsNumber() {
		return totalStarsNumber;
	}
	
	@Override
	public int getStarsNumberDuringSelectedDateRange() {
		return starsNumberDuringSelectedDateRange;
	}

	@Override
	public String getDescrition() {
		return description;
	}
	
}
