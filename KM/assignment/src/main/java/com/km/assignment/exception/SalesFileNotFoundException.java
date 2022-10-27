package com.km.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SalesFileNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public SalesFileNotFoundException(String message) {
        super(message);
    }

    public SalesFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
