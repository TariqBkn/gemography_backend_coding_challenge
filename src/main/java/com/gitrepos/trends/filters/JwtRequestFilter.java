package com.gitrepos.trends.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gitrepos.trends.services_impl.JwtService;
import com.gitrepos.trends.services_impl.MyUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	
	private static final String JWT_NOT_STARTING_WITH_BEARER = "JWT Token does not begin with Bearer String";
	private static final String CANT_AUTHENTICATE = "Can't authenticate!";
	private static final String JWT_EXPIRED = "JWT Token has expired";
	private static final String NOT_A_JWT = "Not a JWT Token";
	private static final String AUTHENTICATION_ERROR = "Authentication error.";
	private static final String CALCULATING_JWT_SIGNATURE_FAILED = "Calculating a signature or verifying an existing signature of a JWT failed";
	private static final String JWT_NOT_CORRECTLY_CONSTRUCTED = "JWT was not correctly constructed.";
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired
	private JwtService jwtService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Get the Authorization header
		final String authorizationHeader = request.getHeader("Authorization");
		String jwt="";
		String username="";
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				jwt = authorizationHeader.substring(7);
				username = jwtService.getUsernameFromToken(jwt);
			} catch (IllegalArgumentException e) {
				logger.warn(NOT_A_JWT,e);
				logger.warn(e.getMessage());
			} catch (ExpiredJwtException e) {
				logger.warn(JWT_EXPIRED);
				logger.warn(e.getMessage());
			} catch(Exception e) {
				logger.warn(CANT_AUTHENTICATE);
				logger.warn(e.getMessage());
			}
		} else {
			logger.warn(JWT_NOT_STARTING_WITH_BEARER);
		}

		// Once we get the token, we validate it.
		if (!jwt.isBlank() && username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

		UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);
			try {
					// if token is valid configure Spring Security to manually set
				// authentication
				if (jwtService.validateToken(jwt, userDetails)) {
	
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					// After setting the Authentication in the context, we specify
					// that the current user is authenticated. So it passes the
					// Spring Security Configurations successfully.
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			} catch (MalformedJwtException e) {
 				logger.warn(JWT_NOT_CORRECTLY_CONSTRUCTED);
 				logger.warn(e.getMessage());
 			}catch (SignatureException e) {
 				logger.warn(CALCULATING_JWT_SIGNATURE_FAILED);
 				logger.warn(e.getMessage());
 			}catch (Exception e) {
 				logger.warn(AUTHENTICATION_ERROR);
 				logger.warn(e.getMessage());
 			}
			
		}
		filterChain.doFilter(request, response);
	}
	

}
