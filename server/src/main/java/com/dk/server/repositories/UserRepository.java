package com.dk.server.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dk.server.models.User;

public interface UserRepository extends MongoRepository<User, String>{
    public User findByUsername( String username );
    public long count();
}
