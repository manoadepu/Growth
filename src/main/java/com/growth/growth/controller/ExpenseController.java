package com.growth.growth.controller;

import com.growth.growth.model.Excercise;
import com.growth.growth.model.Expense;
import com.growth.growth.repository.ExcerciseRepository;
import com.growth.growth.repository.ExpenseRepository;
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
@RequestMapping("/growth/expense")
public class ExpenseController {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    GlobalService globalService;

    @Value("mongo.operations.host")
    String host;

    @Autowired
    private MongoOperations mongoOperation=new MongoTemplate(new MongoClient(host),"growth");

    @PostMapping("/save/{username}")
    public String saveExpense(@PathVariable String username, @RequestBody Expense expense) throws Exception{
        if(globalService.checkIfUserIsRegistered(username)){
            if(expense.getDateTime()==null){
                expense.setDateTime(globalService.getCurrentDateTime());
            }
            expenseRepository.save(expense);
        }
        return "success";
    }

    @GetMapping("/fetch/past/{numberOfDays}")
    public List<Expense> fetchLastFewDaysExpenseData(@PathVariable int numberOfDays) throws Exception{
        Date endDateTime = globalService.getCurrentDateTime();
        Date startDateTime = globalService.minusDaysFromCurrentDay(numberOfDays);
        return (List<Expense>)globalService.fetchDataInRange(startDateTime,endDateTime,Expense.class);
    }
}
