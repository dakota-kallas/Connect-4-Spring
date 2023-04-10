package com.dk.server.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dk.server.models.User;
import com.dk.server.services.UserService;


@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
//	 @RequestMapping(value="/user", method=RequestMethod.GET)
//	 @Nullable
//	 public UserDetails getUser( Principal p ) {
//	 	if( p == null || p.getName() == null ) return null;
//	 	return userService.loadUserByUsername( p.getName() );
//	 }

//	 @GetMapping("/login")
//     public String myPage(HttpSession session) {
//         session.setAttribute("myAttribute", "myValue");
//         return "myPage";
//     }
	
	@GetMapping("/who")
	public User who( Principal p ) {
		if(p != null && p.getName() != null) {
			User user = (User) userService.loadUserByUsername(p.getName());
			System.out.println(user);
			System.out.println("USERNAME: " + user.getUsername() + " | PASSWORD: " + user.getPassword());
			return user;
		}
		else {
			return null;
		}
	}
}

