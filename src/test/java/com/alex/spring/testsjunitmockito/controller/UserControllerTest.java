package com.alex.spring.testsjunitmockito.controller;

import com.alex.spring.testsjunitmockito.exceptions.NotFoundException;
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

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    public static final int ID1 = 1;
    public static final Integer EXPECTED_ID = UserGetCreator.createValidUserGet().getId();
    public static final String EXPECTED_NAME = UserGetCreator.createValidUserGet().getName();
    public static final String EXPECTED_EMAIL = UserGetCreator.createValidUserGet().getEmail();

    public static final String USER_WITH_ID_NOT_FOUND = "User with id `%d` not found";

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userServiceMock;

    @BeforeEach
    void setUp() {
        BDDMockito.when(userServiceMock.findByIdOrThrowNotFoundException(ArgumentMatchers.anyInt()))
                .thenReturn(UserCreator.createValidUser());

        BDDMockito.when(userServiceMock.findAll()).thenReturn(List.of(UserCreator.createValidUser()));
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
    @DisplayName("findById Throws NotFoundException When User Is Not Found")
    void findById_ThrowsNotFoundException_WhenUserIsNotFound() {
        BDDMockito.when(userServiceMock.findByIdOrThrowNotFoundException(ArgumentMatchers.anyInt()))
                .thenThrow(new NotFoundException(USER_WITH_ID_NOT_FOUND.formatted(ID1)));

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> userController.findById(ID1))
                .withMessageContainingAll(USER_WITH_ID_NOT_FOUND.formatted(ID1));
    }

    @Test
    @DisplayName("findAll Returns a List Of UserGet When Successful")
    void findAll_ReturnsAListOfUsersGet_WhenSuccessful() {
        ResponseEntity<List<UserGet>> userGetResponseList = userController.findAll();

        Assertions.assertThat(userGetResponseList.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        Assertions.assertThat(userGetResponseList.getBody()).isNotNull().hasSize(1);
        Assertions.assertThat(userGetResponseList.getBody().get(0)).isNotNull().isExactlyInstanceOf(UserGet.class);
        Assertions.assertThat(userGetResponseList.getBody().get(0).getId()).isNotNull().isEqualTo(EXPECTED_ID);
        Assertions.assertThat(userGetResponseList.getBody().get(0).getName()).isNotNull().isEqualTo(EXPECTED_NAME);
        Assertions.assertThat(userGetResponseList.getBody().get(0).getEmail()).isNotNull().isEqualTo(EXPECTED_EMAIL);
    }

    @Test
    @DisplayName("findAll Returns Empty List When User Is Not Found")
    void findAll_ReturnsEmptyList_WhenUserIsNotFound() {
        BDDMockito.when(userServiceMock.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserGet>> userGetResponseList = userController.findAll();

        Assertions.assertThat(userGetResponseList.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        Assertions.assertThat(userGetResponseList.getBody()).isNotNull().isEmpty();
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