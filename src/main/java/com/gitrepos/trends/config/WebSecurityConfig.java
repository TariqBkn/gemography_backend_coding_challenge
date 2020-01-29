package com.gitrepos.trends.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gitrepos.trends.filters.JwtRequestFilter;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	 
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder() {
			
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				// TODO Auto-generated method stub
				return rawPassword.equals(encodedPassword);
			}
			
			@Override
			public String encode(CharSequence rawPassword) {
				// We'll keep the password unencoded
				return rawPassword.toString();
			}
		};
	}
	// No database is used, user info is hard coded.
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("foo").password("bar").roles("USER");
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// We don't need to worry about CSRF attacks because the browser is authenticating requests by looking at the authorization header
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/api/v1/authenticate").permitAll() 
		.anyRequest().authenticated()
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);// Sessions will not be created
		 // now we have to use the the jwtRequestFilter before UsernamePasswordAuthenticationFilter that processes authentication form submission
		 http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
 		return super.authenticationManagerBean();
	}
	
}
