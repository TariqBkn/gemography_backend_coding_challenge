package com.gitrepos.trends.abstract_models;

public interface IRepositoryFactory {
	public IRepository create(String repositoryName, String description, String programmingLanguage, String totalStarsNumber,
			String starsNumberDuringSelectedDateRange);
}
