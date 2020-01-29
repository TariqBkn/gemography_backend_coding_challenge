package com.gitrepos.trends.models_abstract;

// ICodeRepository represents a repository that can contain plain text, but it definitely contains code written in some programming language.
// ICodeRepository and IReposiry are considered in order not to violate the interface segregation principle.
public interface ICodeRepository extends IRepository {
	
	public String getProgrammingLanguage();
}
