package com.gitrepos.trends.services_impl;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class MyUserDetailsService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// This is where can access the database to load user by username, but we're just going to hard code a user as there is no database. 
		// we're using the default built-in User class with no authorities.
		return new User("user", "pass", new ArrayList<>());
	}

}
