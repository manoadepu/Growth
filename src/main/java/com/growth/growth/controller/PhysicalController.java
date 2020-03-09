package com.growth.growth.controller;

import com.growth.growth.model.Physical;
import com.growth.growth.repository.PhysicalRepository;
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
@RequestMapping("/growth/physical")
public class PhysicalController {
    @Autowired
    PhysicalRepository physicalRepository;

    @Autowired
    GlobalService globalService;

    @Value("mongo.operations.host")
    String host;

    @Autowired
    private MongoOperations mongoOperation=new MongoTemplate(new MongoClient(host),"growth");

    @PostMapping("/save/{username}")
    public String savePhysicalData(@PathVariable String username, @RequestBody Physical physical) throws Exception{
        if(globalService.checkIfUserIsRegistered(username)){
            if(physical.getDateTime()==null){
                physical.setDateTime(globalService.getCurrentDateTime());
            }
            physicalRepository.save(physical);
        }
        return "success";
    }

    @GetMapping("/fetch/past/{numberOfDays}")
    public List<Physical> fetchLastFewDaysPhysicalData(@PathVariable int numberOfDays) throws Exception{
        Date endDateTime = globalService.getCurrentDateTime();
        Date startDateTime = globalService.minusDaysFromCurrentDay(numberOfDays);
        return (List<Physical>)globalService.fetchDataInRange(startDateTime,endDateTime,Physical.class);
    }
}