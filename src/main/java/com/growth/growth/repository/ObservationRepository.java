package com.growth.growth.repository;

import com.growth.growth.model.Excercise;
import com.growth.growth.model.Observation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ObservationRepository extends MongoRepository<Observation, String> {
    List<Observation> findByUsername();
}
