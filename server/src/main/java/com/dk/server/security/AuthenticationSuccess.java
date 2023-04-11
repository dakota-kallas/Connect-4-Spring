package com.dk.server.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.dk.server.models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationSuccess extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {	
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		User user = (User) authentication.getPrincipal();
		
		ObjectMapper objectMapper = new ObjectMapper();
	    JsonNodeFactory nodeFactory = objectMapper.getNodeFactory();
	    ObjectNode jsonObject = nodeFactory.objectNode();
	    
	    JsonNode themeJsonNode = objectMapper.convertValue(user.getDefaults(), JsonNode.class);
	    
	    jsonObject.set("defaults", themeJsonNode);
	    jsonObject.put("email", user.getUsername());
	    
	    response.getOutputStream().print(objectMapper.writeValueAsString(jsonObject));

	}
}
