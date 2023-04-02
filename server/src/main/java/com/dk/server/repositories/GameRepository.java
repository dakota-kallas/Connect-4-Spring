package com.dk.server.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dk.server.models.Game;

public interface GameRepository extends MongoRepository<Game, String> {
    List<Game> findByOwner( String owner );

    // TODO: FIX THIS
    Game findOne(String id);
}
