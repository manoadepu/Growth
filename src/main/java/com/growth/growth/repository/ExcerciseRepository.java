package com.growth.growth.repository;

import com.growth.growth.model.Excercise;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface ExcerciseRepository extends MongoRepository<Excercise, String> {
    List<Excercise> findByUsername(String username);

}
