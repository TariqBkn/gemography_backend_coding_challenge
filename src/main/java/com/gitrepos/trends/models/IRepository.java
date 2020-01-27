package com.gitrepos.trends.models;

import java.util.Optional;
/**
 * This interface represents a generic Git repository
 * @author bkn_tariq
 *
 */
public interface IRepository {
	
	public String getRepositoryName();
	
	// Some repositories don't contain code (eg. only a README.md)
	public Optional<String> getProgrammingLanguage();
	
	public int getTotalStarsNumber();
	
	public int getStarsNumberDuringSelectedDateRange();
	
}
