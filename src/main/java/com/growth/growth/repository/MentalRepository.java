package com.growth.growth.repository;

import com.growth.growth.model.Mental;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MentalRepository extends MongoRepository<Mental, String> {
    List<Mental> findByUsername();
}
