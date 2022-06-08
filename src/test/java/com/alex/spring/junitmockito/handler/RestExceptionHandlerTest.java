package com.alex.spring.junitmockito.handler;

import com.alex.spring.junitmockito.exceptions.EmailAlreadyExistsException;
import com.alex.spring.junitmockito.exceptions.ExceptionDetails;
import com.alex.spring.junitmockito.exceptions.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("handlerNotFoundException Returns ResponseEntity<ExceptionDetails> When NotFoundException Is Called")
    void handlerNotFoundException_ReturnsResponseEntityOfExceptionDetails_WhenNotFoundExceptionIsCalled() {
        ResponseEntity<ExceptionDetails> nfe = restExceptionHandler
                .handlerNotFoundException(new NotFoundException("Object Not Found"));

        Assertions.assertThat(nfe).isNotNull().isExactlyInstanceOf(ResponseEntity.class);
        Assertions.assertThat(nfe.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        Assertions.assertThat(nfe.getBody()).isNotNull().isExactlyInstanceOf(ExceptionDetails.class);
        Assertions.assertThat(nfe.getBody().getTimeStamp()).isBefore(LocalDateTime.now());
        Assertions.assertThat(nfe.getBody().getTitle()).isEqualTo("Not found Exception, Check the Documentation");
        Assertions.assertThat(nfe.getBody().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        Assertions.assertThat(nfe.getBody().getDetails()).isEqualTo("Object Not Found");
    }

    @Test
    @DisplayName("handlerEmailAlreadyExists Returns RE<ExceptionDetails> When EmailAlreadyExceptionIsCalled")
    void handlerEmailAlreadyExists_ReturnsResponseEntityOfExceptionDetails_WhenEmailAlreadyExistsExceptionIsCalled() {
        ResponseEntity<ExceptionDetails> eae = restExceptionHandler
                .handlerEmailAlreadyExists(new EmailAlreadyExistsException("Email Already Exists"));

        Assertions.assertThat(eae).isNotNull().isExactlyInstanceOf(ResponseEntity.class);
        Assertions.assertThat(eae.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

        Assertions.assertThat(eae.getBody()).isNotNull().isExactlyInstanceOf(ExceptionDetails.class);
        Assertions.assertThat(eae.getBody().getTimeStamp()).isBefore(LocalDateTime.now());
        Assertions.assertThat(eae.getBody().getTitle()).isEqualTo("Email Already Exists Exception, Check the Documentation");
        Assertions.assertThat(eae.getBody().getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        Assertions.assertThat(eae.getBody().getDetails()).isEqualTo("Email Already Exists");
    }
}