package com.gitrepos.trends.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gitrepos.trends.models_impl.AuthenticationRequest;
import com.gitrepos.trends.response_helpers.ResponseBody;
import com.gitrepos.trends.services_impl.JwtService;
import com.gitrepos.trends.services_impl.MyUserDetailsService;

@RestController
@RequestMapping("/api/v1/")
public class AuthenticationController {
	private static final String JWT_GENERATED = "Authenticated - JWT generated successfuly";
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/authenticate")
	public ResponseEntity<ResponseBody> authenticate(@RequestBody AuthenticationRequest authRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
					);
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authRequest.getUsername());
		final String jwt = jwtService.generateToken(userDetails);
		return ResponseEntity.ok().body(new ResponseBody(JWT_GENERATED,jwt));
	}
}
