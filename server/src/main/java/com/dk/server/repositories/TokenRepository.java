package com.dk.server.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dk.server.models.Token;

public interface TokenRepository extends MongoRepository<Token, String> {
    List<Token> findAll();
    
    Token findByName(String name);
    
    public long count();
}
