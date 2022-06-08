package com.alex.spring.testsjunitmockito.handler;

import com.alex.spring.testsjunitmockito.exceptions.ExceptionDetails;
import com.alex.spring.testsjunitmockito.exceptions.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@SpringBootTest
class RestExceptionHandlerTest {

    @InjectMocks
    private RestExceptionHandler restExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handlerNotFoundException_ReturnsResponseEntityOfExceptionDetails_WhenNotFoundExceptionIsCalled() {
        ResponseEntity<ExceptionDetails> nfe = restExceptionHandler
                .handlerNotFoundException(new NotFoundException("Object Not Found"));

        Assertions.assertThat(nfe).isNotNull().isExactlyInstanceOf(ResponseEntity.class);
        Assertions.assertThat(nfe.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);

        Assertions.assertThat(nfe.getBody()).isNotNull().isExactlyInstanceOf(ExceptionDetails.class);
        Assertions.assertThat(nfe.getBody().getTimeStamp()).isBefore(LocalDateTime.now());
        Assertions.assertThat(nfe.getBody().getTitle()).isEqualTo("Not found Exception, Check the Documentation");
        Assertions.assertThat(nfe.getBody().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        Assertions.assertThat(nfe.getBody().getDetails()).isEqualTo("Object Not Found");
    }

    @Test
    void handlerEmailAlreadyExists() {
    }
}