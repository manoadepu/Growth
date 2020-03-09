package com.growth.growth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@Document(collection = "Expense")
public class Expense {
    String username;
    String expenseType;
    String amount;
    Date dateTime;
}
