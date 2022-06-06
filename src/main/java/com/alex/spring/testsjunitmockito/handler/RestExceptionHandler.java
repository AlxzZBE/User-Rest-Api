package com.alex.spring.testsjunitmockito.handler;

import com.alex.spring.testsjunitmockito.exceptions.EmailAlreadyExistsException;
import com.alex.spring.testsjunitmockito.exceptions.ExceptionDetails;
import com.alex.spring.testsjunitmockito.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerNotFoundException(NotFoundException nfe) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .timeStamp(LocalDateTime.now())
                        .title("Not found Exception, Check the Documentation")
                        .status(HttpStatus.NOT_FOUND.value())
                        .details(nfe.getMessage())
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ExceptionDetails> handlerEmailAlreadyExists(EmailAlreadyExistsException eae) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .timeStamp(LocalDateTime.now())
                        .title("Email Already Exists Exception, Check the Documentation")
                        .status(HttpStatus.CONFLICT.value())
                        .details(eae.getMessage())
                        .build(), HttpStatus.CONFLICT);
    }

}
