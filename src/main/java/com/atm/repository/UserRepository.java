package com.atm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.atm.model.Profile;

@Repository
public interface UserRepository extends MongoRepository<Profile, String> {
}