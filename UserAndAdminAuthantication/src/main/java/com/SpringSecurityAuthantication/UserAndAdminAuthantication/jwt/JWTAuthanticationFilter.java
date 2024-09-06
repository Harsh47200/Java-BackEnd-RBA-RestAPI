package com.SpringSecurityAuthantication.UserAndAdminAuthantication.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component

//The class extends OncePerRequestFilter to ensure that it is executed only once per request.
public class JWTAuthanticationFilter extends OncePerRequestFilter {

	// Autowires the JWTHelper and UserDetailsService beans into the filter.
	// JWTHelper is used for JWT operations, and UserDetailsService is used to load
	// user details.
	@Autowired
	private JWTHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	// Overrides the doFilterInternal method from OncePerRequestFilter to implement the filtering logic.
	// It processes the request and response and invokes the next filter in the chain.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Retrieves the Authorization header from the HTTP request
		String requestHeader = request.getHeader("Authorization");

		// Initializes variables username and token.
		// Checks if the Authorization header is not null and starts with "Bearer" to
		// ensure it contains a JWT token.
		String username = null;
		String token = null;
		if (requestHeader != null && requestHeader.startsWith("Bearer")) {
			// Extracts the JWT token from the Authorization header by removing the "Bearer
			// " prefix.
			token = requestHeader.substring(7);

			// Attempts to get the username from the token using the JWTHelper class.
			try {

				username = this.jwtHelper.getUsernameFromToken(token);

			}
			// IllegalArgumentException: Token is illegal or invalid.
			// ExpiredJwtException: Token has expired.
			// MalformedJwtException: Token is malformed.
			// Generic Exception: Handles any other exceptions
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (ExpiredJwtException e) {
				e.printStackTrace();
			} catch (MalformedJwtException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		// Logs an informational message if the Authorization header does not start with
		// "Bearer".
		else {
			logger.info("Invalid Header Value !! ");
		}
		// Checks if the username is not null and if no authentication is present in the
		// SecurityContext.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// Loads the UserDetails for the username and validates the JWT token using the
			// JWTHelper class.
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);

			// Creates a UsernamePasswordAuthenticationToken with userDetails, no
			// credentials, and the user's authorities.
			// Sets details for the authentication token using
			// WebAuthenticationDetailsSource.
			// Sets the authentication in the SecurityContextHolder.
			// Logs a message if validation fails.
			if (validateToken) {

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);

			} else {
				logger.info("Validation fails !!");
			}

		}
		// Passes the request and response to the next filter in the chain.
		filterChain.doFilter(request, response);

	}
}
