package com.growth.growth.service;

import com.growth.growth.exception.UserNotRegisteredException;
import com.growth.growth.model.*;
import com.growth.growth.repository.UserDetailsRepository;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class GlobalService {
    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Value("mongo.operations.host")
    String host;

    @Autowired
    private MongoOperations mongoOperation=new MongoTemplate(new MongoClient(host),"growth");

    public boolean checkIfUserIsRegistered(String username) throws Exception{
        Boolean isRegisteredUser = false;
        if(!userDetailsRepository.findByUsername(username).getUsername().isEmpty()){
            isRegisteredUser = true;
        }
        else {
            throw new UserNotRegisteredException(username);
        }
        return isRegisteredUser;
    }

    public Date getCurrentDateTime() throws Exception{
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        Date date = new Date();
//        String date1 = dateFormat.format(date);
//        Date dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(date1);

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'").parse(nowAsISO);

    }

    public Date minusDaysFromCurrentDay(int numberOfDays) throws Exception{
        Date currentDateTime = getCurrentDateTime();

        LocalDateTime localDateTime = currentDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.minusDays(numberOfDays);

        //convert localdateTime to date
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Object fetchDataInRange(Date startDateTime, Date endDateTime, Class c){
        Criteria criteria = Criteria.where("dateTime").gte(startDateTime).lt(endDateTime);
        Query query = new Query(criteria);
        Object o = new Object();
        if(c.getName().equals("Excercise")){
            List<Excercise> excercisesData = mongoOperation.find(query, Excercise.class);
        }
        if(c.getName().equals("Expense")){
            List<Expense> expensesData = mongoOperation.find(query, Expense.class);
        }
        if(c.getName().equals("Mental")){
            List<Mental> excercisesData = mongoOperation.find(query, Mental.class);
        }
        if(c.getName().equals("Observation")){
            List<Observation> excercisesData = mongoOperation.find(query, Observation.class);

        }
        if(c.getName().equals("Physical")){
            List<Physical> excercisesData = mongoOperation.find(query, Physical.class);
        }

        return o;
    }

}
