package com.growth.growth.exception;

public class UserNotRegisteredException extends RuntimeException{
    public UserNotRegisteredException(String username) {
        super(username + " not registered");
    }
}
