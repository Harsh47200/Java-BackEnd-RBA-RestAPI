package com.SpringSecurityAuthantication.UserAndAdminAuthantication.config;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Spring annotation to mark the class as a Spring-managed bean.
@Component

//AccessDeniedHandler interface to handle access denial scenarios
public class IlleagalAccess implements AccessDeniedHandler {

	// Overrides the handle method from the AccessDeniedHandler interface. This
	// method is invoked when an access denial occurs
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// Sets the HTTP response status code to 403 (Forbidden)
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		// This tells the client that the response body will be in JSON format.
		response.setContentType("application/json");
		// providing information about why access was denied.
		String errorMessage = "Access Denied: You do not have permission to access this resource.";
		// This sends the message back to the client to inform them of the access
		// denial.
		response.getWriter().write(errorMessage);
	}

}
