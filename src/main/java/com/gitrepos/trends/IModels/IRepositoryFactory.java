package com.gitrepos.trends.IModels;

public interface IRepositoryFactory {
	public IRepository create(String repositoryName, String description, String programmingLanguage, String totalStarsNumber,
			String starsNumberDuringSelectedDateRange);
}