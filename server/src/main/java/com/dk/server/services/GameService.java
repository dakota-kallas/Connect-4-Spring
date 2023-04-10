package com.dk.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dk.server.models.Game;
import com.dk.server.models.Theme;
import com.dk.server.repositories.GameRepository;
import com.dk.server.repositories.TokenRepository;

@Service
public class GameService {
	@Autowired
	private GameRepository gameRepo;
	@Autowired
	private TokenRepository tokenRepo;
	
	public Game findById(String gid) {
		return gameRepo.findById( gid ).get();
	}
	
	public List<Game> findByOwner(String username) {
		return gameRepo.findByOwner(username);
	}

	public Game deleteById(String id) {
		Game oldGame = findById( id );
		gameRepo.delete( oldGame );
		return oldGame;
	}

	public Game createGame(String color, String playerToken, String computerToken, String owner) {
		Theme theme = new Theme.Builder()
        		.color("#" + color)
        		.playerToken(tokenRepo.findByName(playerToken))
        		.computerToken(tokenRepo.findByName(computerToken))
        		.build();
		
		return gameRepo.save(new Game.Builder()
				.owner(owner)
				.theme(theme)
				.build()
				);
	}
	
	public Game updateGame(Game g) {
		return gameRepo.save(g);
	}

}
