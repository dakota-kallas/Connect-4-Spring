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
	public Game findGame( Principal p, @PathVariable String gid ) throws BadRequestException {
		validateOwner( p );
		validateGameOwner( gid, p.getName() );
		return gameService.findById(gid);
	}
	
	@PostMapping("/gids/{gid}")
	public Game makeMove( Principal p, @PathVariable String gid,  @RequestParam(required=true) String move) throws BadRequestException {
		Game game = gameService.findById(gid);
		int nextRow = gameService.findNextAvailableSlot(game, Integer.parseInt(move));
		if(nextRow < 0 || nextRow > 4) {
			throw new BadRequestException();
		}
		else {
			game = gameService.addToken(game, nextRow, Integer.parseInt(move));
		}
		return gameService.findById(gid);
	}
	
	@PostMapping("/")
	public Game createGame( @RequestBody CreateGameRequest body, Principal p, @RequestParam(required=true) String color ) throws BadRequestException {
		if(body.computerToken == null || body.playerToken == null || color == null)
		{
			throw new BadRequestException();
		}
		validateOwner( p );
		return gameService.createGame(color, body.getPlayerToken(), body.getComputerToken(), p.getName());
	}

	public static class CreateGameRequest {
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
	}

}
