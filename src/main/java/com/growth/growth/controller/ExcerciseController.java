package com.growth.growth.controller;

import com.growth.growth.model.Excercise;
import com.growth.growth.repository.ExcerciseRepository;
import com.growth.growth.service.GlobalService;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/growth/excercise")
public class ExcerciseController {

    @Autowired
    ExcerciseRepository excerciseRepository;

    @Autowired
    GlobalService globalService;

    @Value("mongo.database.name")
    String database;

    @PostMapping("/save/{username}")
    public String saveExcercise(@PathVariable String username, @RequestBody Excercise excercise) throws Exception{
        excercise.setUsername(username);
        if(globalService.checkIfUserIsRegistered(username)){
            if(excercise.getDateTime()==null){
                excercise.setDateTime(globalService.getCurrentDateTime());
            }
            excerciseRepository.save(excercise);
        }
        return "success";
    }

    @GetMapping("/fetch/{username}")
    public void fetchExcerciseData(@PathVariable String username){
        excerciseRepository.findByUsername(username);
    }

    @GetMapping("/fetch/past/{numberOfDays}")
    public List<Excercise> fetchLastFewDaysExcerciseData(@PathVariable int numberOfDays) throws Exception{
        Date endDateTime = globalService.getCurrentDateTime();
        Date startDateTime = globalService.minusDaysFromCurrentDay(numberOfDays);
        return (List<Excercise>)globalService.fetchDataInRange(startDateTime,endDateTime,Excercise.class);
    }

}
