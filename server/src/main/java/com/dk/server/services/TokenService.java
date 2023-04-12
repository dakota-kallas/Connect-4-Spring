package com.dk.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dk.server.models.Token;
import com.dk.server.repositories.TokenRepository;

@Service
public class TokenService {
	@Autowired
	private TokenRepository tokenRepo;
	
	public Token getTokenFromName(String name) {
		return tokenRepo.findByName(name);
	}
}
