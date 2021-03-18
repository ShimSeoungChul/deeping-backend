package com.deeping.service;

public class PhoneNotExistedException extends RuntimeException{
    PhoneNotExistedException(String phone){
        super("Phone is not registed: " + phone);
    }
}
