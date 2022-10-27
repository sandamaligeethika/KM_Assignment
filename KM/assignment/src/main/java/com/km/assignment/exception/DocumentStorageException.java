package com.km.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DocumentStorageException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public DocumentStorageException(String message){
        super(message);
    }

    public DocumentStorageException(String message, Throwable cause){
        super(message, cause);
    }
}
