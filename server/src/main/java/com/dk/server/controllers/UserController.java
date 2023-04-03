package com.dk.server.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dk.server.services.UserService;


@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/user", method=RequestMethod.GET)
	@Nullable
	public UserDetails getUser( Principal p ) {
		if( p == null || p.getName() == null ) return null;
		return userService.loadUserByUsername( p.getName() );
	}
}

