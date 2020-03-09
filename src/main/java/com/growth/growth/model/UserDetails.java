package com.growth.growth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "UserDetails")
@Builder
@Getter
@Setter
public class UserDetails {
    @Id
    private String username;

    private String email;
    private String password;
}
