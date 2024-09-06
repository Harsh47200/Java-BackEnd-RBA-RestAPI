package com.SpringSecurityAuthantication.UserAndAdminAuthantication.jwt;

import java.io.IOException;

import java.io.PrintWriter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Spring annotation to mark the class as a Spring-managed bean.
@Component

//The class implements AuthenticationEntryPoint, which is used to handle unauthorized access attempts.
public class JWTAuthanticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// Sets the HTTP response status to 401 Unauthorized, indicating that the
		// request lacks valid credentials.
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		// Sends a message to the client, including the details of the authentication
		// failure. This message will be displayed in the response body
		PrintWriter writer = response.getWriter();
		writer.println("Access Denied !! " + authException.getMessage());
	}

}
