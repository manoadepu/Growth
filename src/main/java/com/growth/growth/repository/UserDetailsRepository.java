package com.growth.growth.repository;

import com.growth.growth.model.UserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserDetailsRepository extends MongoRepository<UserDetails, String> {
    UserDetails findByUsername(String username);
}
