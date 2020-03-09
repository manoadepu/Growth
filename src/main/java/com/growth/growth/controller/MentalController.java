package com.growth.growth.controller;

import com.growth.growth.model.Expense;
import com.growth.growth.model.Mental;
import com.growth.growth.repository.ExpenseRepository;
import com.growth.growth.repository.MentalRepository;
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
@RequestMapping("/growth/mental")
public class MentalController {

    @Autowired
    MentalRepository mentalRepository;

    @Autowired
    GlobalService globalService;

    @Value("mongo.operations.host")
    String host;

    @Autowired
    private MongoOperations mongoOperation=new MongoTemplate(new MongoClient(host),"growth");

    @PostMapping("/save/{username}")
    public String saveMentalData(@PathVariable String username, @RequestBody Mental mental) throws Exception{
        if(globalService.checkIfUserIsRegistered(username)){
            if(mental.getDateTime()==null){
                mental.setDateTime(globalService.getCurrentDateTime());
            }
            mentalRepository.save(mental);
        }
        return "success";
    }

    @GetMapping("/fetch/past/{numberOfDays}")
    public List<Mental> fetchLastFewDaysMentalData(@PathVariable int numberOfDays) throws Exception{
        Date endDateTime = globalService.getCurrentDateTime();
        Date startDateTime = globalService.minusDaysFromCurrentDay(numberOfDays);
        return (List<Mental>)globalService.fetchDataInRange(startDateTime,endDateTime,Mental.class);
    }
}
