package com.alex.spring.junitmockito.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ExceptionDetails {
    protected LocalDateTime timeStamp;
    protected String title;
    protected int status;
    protected String details;
}
