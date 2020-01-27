package com.gitrepos.trends.models;

import java.util.List;
/**
 * This interface represents a generic Git repository
 * @author bkn_tariq
 *
 */
public interface IRepository {
	
	public String getRepositoryName();
	
	// Multiple languages can be used in a single repository
	public List<String> getProgrammingLanguage();
	
	// Multiple frameworks can be used in a single repository
	public List<String> getUsedFrameworkName();
	
	public int getStarsCount();
}
