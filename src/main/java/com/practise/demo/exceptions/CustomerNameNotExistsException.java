package com.practise.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerNameNotExistsException extends RuntimeException{
    public CustomerNameNotExistsException(String message){
        super(message);
    }
}
