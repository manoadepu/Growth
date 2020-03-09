package com.growth.growth.repository;

import com.growth.growth.model.Excercise;
import com.growth.growth.model.Physical;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PhysicalRepository extends MongoRepository<Physical, String> {
    List<Physical> findByUsername();
}
