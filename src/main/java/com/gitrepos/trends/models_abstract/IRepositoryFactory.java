package com.gitrepos.trends.models_abstract;

public interface IRepositoryFactory {
	public IRepository create(String repositoryName, String description, String programmingLanguage, String totalStarsNumber,
			String starsNumberDuringSelectedDateRange);
}
