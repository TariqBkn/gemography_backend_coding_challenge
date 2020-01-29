package com.gitrepos.trends.models_impl;
/**
 * Helps change the date range of trending repositories
 * @author bkn_tariq
 *
 */
public enum DateRange {
	DAILY(""), WEEKLY("weekly"), MONTHLY("monthly");
	
	private String dateRangeValue;
	 
    DateRange(String dateRangeValue) {
        this.dateRangeValue = dateRangeValue;
    }
 
    public String getValue() {
        return dateRangeValue;
    }
}
