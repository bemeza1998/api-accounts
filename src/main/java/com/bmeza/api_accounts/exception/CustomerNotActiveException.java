package com.bmeza.api_accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class CustomerNotActiveException extends RuntimeException {

    public CustomerNotActiveException(String message) {
        super(message);
      }
    
}

