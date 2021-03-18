package com.deeping.service;

public class PhoneExistedException extends RuntimeException{

    public PhoneExistedException(String phone){
        super("Phone is already registed: " + phone);
    }
}
