package com.deeping.web;

public class PasswordWrongException extends RuntimeException{
    public PasswordWrongException(String phone, String password){
        super("Password is wrong: "+phone+", "+password);
    }
}
