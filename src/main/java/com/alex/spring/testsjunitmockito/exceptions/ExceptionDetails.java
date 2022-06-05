package com.alex.spring.testsjunitmockito.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionDetails {
    protected LocalDateTime timeStamp;
    protected String title;
    protected int status;
    protected String details;
}
