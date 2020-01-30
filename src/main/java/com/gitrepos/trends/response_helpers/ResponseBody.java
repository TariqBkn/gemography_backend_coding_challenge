package com.gitrepos.trends.response_helpers;
/**
 * This is the body of response entities return by the controllers
 * @author bkn_tariq
 *
 */
public class ResponseBody {
   
	protected String message;
    protected Object value;
    
	public ResponseBody(String message, Object value) {
		this.message = message;
		this.value=value;
	}
	
	public String getMessage() {
		return message;
	}
	public Object getValue() {
		return value;
	}
}
