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
@Document(collection = "Physical")
public class Physical {
    String username;
    String time;
    String notes;
    Date dateTime;
}
