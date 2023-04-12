package com.dk.server.security;

import java.io.IOException;
import com.dk.server.models.Error;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFailure extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException,
			ServletException {		
		Error error = new Error.Builder().msg("Invalid credentials.").build();
		
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		
		ObjectMapper objectMapper = new ObjectMapper();
	    JsonNodeFactory nodeFactory = objectMapper.getNodeFactory();
	    ObjectNode jsonObject = nodeFactory.objectNode();
	    
	    jsonObject.put("msg", error.getMsg());
	    
	    response.getOutputStream().print(objectMapper.writeValueAsString(jsonObject));
	}	
}
