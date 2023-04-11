package com.dk.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dk.server.models.Metadata;
import com.dk.server.models.Theme;
import com.dk.server.models.Token;
import com.dk.server.repositories.TokenRepository;

@Service
public class MetaService {
	@Autowired
	private TokenRepository tokenRepo;

	public Metadata getMeta() {
		Theme theme = new Theme.Builder()
        		.color("#fff000")
        		.playerToken(tokenRepo.findByName("Western"))
        		.computerToken(tokenRepo.findByName("Bullet Train"))
        		.build();
		List<Token> tokens = tokenRepo.findAll();
		
		return new Metadata.Builder()
				.defaultTheme(theme)
				.tokens(tokens)
				.build();
	}
}
