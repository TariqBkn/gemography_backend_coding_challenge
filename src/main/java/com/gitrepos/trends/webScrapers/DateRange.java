package com.gitrepos.trends.webScrapers;
/**
 * Helps change the date range of trending repositories
 * @author bkn_tariq
 *
 */
public enum DateRange {
	DAILY(""), WEEKLY("weekly"), MONTHLY("monthly");
	
	private String dateRange;
	 
    DateRange(String period) {
        this.dateRange = period;
    }
 
    public String getValue() {
        return dateRange;
    }
}
