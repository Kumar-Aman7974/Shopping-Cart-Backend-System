package com.dailycodework.demoshops.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex)
    {
        String message = "You do not have permission to access this resource";

        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);

    }


}
