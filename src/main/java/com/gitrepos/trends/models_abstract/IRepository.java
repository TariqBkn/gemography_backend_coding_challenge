package com.gitrepos.trends.models_abstract;

// This interface represents a repository that does not necessarily contain code (only a README.md file for example.)
// Example of such a repository : https://github.com/trimstray/the-book-of-secret-knowledge

public interface IRepository {
	
	public String getRepositoryName();
	
	public int getTotalStarsNumber();
	
	public int getStarsNumberDuringSelectedDateRange();
	
	public String getDescription();	
}
