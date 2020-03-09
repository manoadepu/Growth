package com.growth.growth.controller;

import com.growth.growth.model.Expense;
import com.growth.growth.model.Observation;
import com.growth.growth.repository.ExpenseRepository;
import com.growth.growth.repository.MentalRepository;
import com.growth.growth.repository.ObservationRepository;
import com.growth.growth.service.GlobalService;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/growth/observation")
public class ObservationController {
    @Autowired
    ObservationRepository observationRepository;

    @Autowired
    GlobalService globalService;

    @Value("mongo.operations.host")
    String host;

    @Autowired
    private MongoOperations mongoOperation=new MongoTemplate(new MongoClient(host),"growth");

    @PostMapping("/save/{username}")
    public String saveObservation(@PathVariable String username, @RequestBody Observation observation) throws Exception{
        if(globalService.checkIfUserIsRegistered(username)){
            if(observation.getDateTime()==null){
                observation.setDateTime(globalService.getCurrentDateTime());
            }
            observationRepository.save(observation);
        }
        return "success";
    }

    @GetMapping("/fetch/past/{numberOfDays}")
    public List<Observation> fetchLastFewDaysObservationData(@PathVariable int numberOfDays) throws Exception{
        Date endDateTime = globalService.getCurrentDateTime();
        Date startDateTime = globalService.minusDaysFromCurrentDay(numberOfDays);
        return (List<Observation>)globalService.fetchDataInRange(startDateTime,endDateTime,Observation.class);
    }
}
