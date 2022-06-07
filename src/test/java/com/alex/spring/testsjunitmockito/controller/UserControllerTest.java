package com.alex.spring.testsjunitmockito.controller;

import com.alex.spring.testsjunitmockito.requests.UserGet;
import com.alex.spring.testsjunitmockito.services.UserService;
import com.alex.spring.testsjunitmockito.util.UserCreator;
import com.alex.spring.testsjunitmockito.util.UserGetCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    public static final int ID1 = 1;
    public static final Integer EXPECTED_ID = UserGetCreator.createValidUserGet().getId();
    public static final String EXPECTED_NAME = UserGetCreator.createValidUserGet().getName();
    public static final String EXPECTED_EMAIL = UserGetCreator.createValidUserGet().getEmail();

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        BDDMockito.when(userService.findByIdOrThrowNotFoundException(ArgumentMatchers.anyInt()))
                .thenReturn(UserCreator.createValidUser());
    }

    @Test
    @DisplayName("findById Returns UserGet When Successful")
    void findById_ReturnsUserGet_WhenSuccessful() {
        ResponseEntity<UserGet> userGetResponse = userController.findById(ID1);

        Assertions.assertThat(userGetResponse.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        Assertions.assertThat(userGetResponse.getBody()).isNotNull().isExactlyInstanceOf(UserGet.class);

        Assertions.assertThat(userGetResponse.getBody().getId()).isNotNull().isEqualTo(EXPECTED_ID);
        Assertions.assertThat(userGetResponse.getBody().getName()).isNotNull().isEqualTo(EXPECTED_NAME);
        Assertions.assertThat(userGetResponse.getBody().getEmail()).isNotNull().isEqualTo(EXPECTED_EMAIL);
    }

    @Test
    void findAll() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findByName() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}