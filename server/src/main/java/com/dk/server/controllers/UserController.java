package com.dk.server.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dk.server.models.Metadata;
import com.dk.server.models.Theme;
import com.dk.server.models.User;
import com.dk.server.security.BadRequestException;
import com.dk.server.services.MetaService;
import com.dk.server.services.TokenService;
import com.dk.server.services.UserService;

@RestController
@RequestMapping( "/api/v2/" )
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private MetaService metaService;

	@GetMapping("/meta/")
	public Metadata meta(Principal p) {
		return metaService.getMeta();
	}

	@GetMapping("/who/")
	public User who(Principal p) throws BadRequestException {
		if (p != null && p.getName() != null) {
			User user = (User) userService.loadUserByUsername(p.getName());
			return user;
		} else {
			return null;
		}
	}
	
	@PutMapping("/defaults/")
	public Theme setDefaultTheme( Principal p, @RequestBody UpdateThemeRequest body ) throws BadRequestException {
		if(body.computerToken == null || body.playerToken == null || body.color == null)
		{
			throw new BadRequestException();
		}
		User user = who(p);
		Theme theme = new Theme.Builder()
				.color(body.getColor())
				.playerToken(tokenService.getTokenFromName(body.getPlayerToken()))
				.computerToken(tokenService.getTokenFromName(body.getComputerToken()))
				.build();
		user.setDefaults(theme);
		userService.updateUser(user);
		return theme;
	}
	
	public static class UpdateThemeRequest {
	    private String color;
	    private String playerToken;
	    private String computerToken;
	    
	    public String getPlayerToken() {
	        return playerToken;
	    }

	    public void setPlayerToken(String playerToken) {
	        this.playerToken = playerToken;
	    }

	    public String getComputerToken() {
	        return computerToken;
	    }

	    public void setComputerToken(String computerToken) {
	        this.computerToken = computerToken;
	    }

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}
	}
}
