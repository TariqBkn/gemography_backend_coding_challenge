package com.gitrepos.trends.models_impl;

public class AuthenticationResponse {
	private final String jwt;
	
	public AuthenticationResponse(String jwt) {
		this.jwt=jwt;
	}
	
	public String getJwt() {
		return jwt;
	}
}
