package com.alex.spring.testsjunitmockito.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter @Setter
@Builder
public class ExceptionDetails {
    protected LocalDateTime timeStamp;
    protected String title;
    protected int status;
    protected String details;
}
