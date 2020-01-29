package com.gitrepos.trends.models_impl;

import com.gitrepos.trends.models_abstract.IRepository;

// A GitHub repository that contains plain text only
public class GithubPlainTextRepository implements IRepository {
	protected final String repositoryName;
	protected final int totalStarsNumber;
	protected final int starsNumberDuringSelectedDateRange;
	protected final String description;

	public GithubPlainTextRepository(String repositoryName, String description, int totalStarsNumber,
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
	public String getDescription() {
		return description;
	}
	
}
