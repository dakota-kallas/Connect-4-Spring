package com.dk.server.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dk.server.models.Game;
import com.dk.server.security.BadRequestException;
import com.dk.server.services.GameService;
import com.dk.server.services.UserService;

@RestController
@RequestMapping( "/api/v2/" )
public class GameController {
	@Autowired
	private GameService gameService;
	
	@Autowired
	private UserService userService;

	private void validateOwner( Principal p ) throws BadRequestException {
		if( p == null || userService.loadUserByUsername(p.getName()) == null ) throw new BadRequestException(); 
	}
	
	private void validateGameOwner( String gid, String uname ) throws BadRequestException {
		Game gOld = gameService.findById( gid );		
		if( !gOld.getOwner().equals(uname) ) throw new BadRequestException();
	}
	
	@GetMapping("/")
	public List<Game> findAll( Principal p ) throws BadRequestException {
		validateOwner( p );
		return gameService.findByOwner(p.getName());
	}
	
	@GetMapping("/gids/{gid}")
	public Game findGame( Principal p, @RequestParam(required=true) String gid ) throws BadRequestException {
		validateOwner( p );
		validateGameOwner( p.getName(), gid );
		return gameService.findById(gid);
	}
	
	@PostMapping("/")
	public Game createGame( @RequestBody String playerToken, @RequestBody String computerToken, Principal p, @RequestParam(required=true) String color ) throws BadRequestException {
		validateOwner( p );
		return gameService.createGame(color, playerToken, computerToken, p.getName());
	}



}
