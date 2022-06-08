package com.alex.spring.testsjunitmockito.controller;

import com.alex.spring.testsjunitmockito.exceptions.EmailAlreadyExistsException;
import com.alex.spring.testsjunitmockito.exceptions.NotFoundException;
import com.alex.spring.testsjunitmockito.requests.UserGet;
import com.alex.spring.testsjunitmockito.requests.UserPostRequestBody;
import com.alex.spring.testsjunitmockito.requests.UserPutRequestBody;
import com.alex.spring.testsjunitmockito.services.UserService;
import com.alex.spring.testsjunitmockito.util.UserCreator;
import com.alex.spring.testsjunitmockito.util.UserGetCreator;
import com.alex.spring.testsjunitmockito.util.UserPostRequestBodyCreator;
import com.alex.spring.testsjunitmockito.util.UserPutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    public static final int ID1 = 1;
    public static final String EMAIL1 = "email1@gmail.com";
    public static final Integer EXPECTED_ID = UserGetCreator.createValidUserGet().getId();
    public static final String EXPECTED_NAME = UserGetCreator.createValidUserGet().getName();
    public static final String EXPECTED_EMAIL = UserGetCreator.createValidUserGet().getEmail();
    public static final String USER_WITH_ID_NOT_FOUND = "User with id `%d` not found";
    public static final String USER_WITH_EMAIL_NOT_FOUND = "User with email `%s` not found";
    public static final String USER_WITH_EMAIL_ALREADY_EXISTS = "User with email `%s` Already Exists!";

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userServiceMock;

    @BeforeEach
    void setUp() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attributes);

        BDDMockito.when(userServiceMock.findByIdOrThrowNotFoundException(ArgumentMatchers.anyInt()))
                .thenReturn(UserCreator.createValidUser());

        BDDMockito.when(userServiceMock.findAll()).thenReturn(List.of(UserCreator.createValidUser()));

        BDDMockito.when(userServiceMock.findByEmailOrThrowNotFoundException(ArgumentMatchers.anyString()))
                .thenReturn(UserCreator.createValidUser());

        BDDMockito.when(userServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(UserCreator.createValidUser()));

        BDDMockito.when(userServiceMock.save(ArgumentMatchers.any(UserPostRequestBody.class)))
                .thenReturn(UserCreator.createValidUser());

        BDDMockito.when(userServiceMock.update(ArgumentMatchers.any(UserPutRequestBody.class)))
                .thenReturn(UserCreator.createValidUser());

        BDDMockito.doNothing().when(userServiceMock).deleteById(ArgumentMatchers.anyInt());
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
    @DisplayName("findByEmail Returns UserGet When Successful")
    void findByEmail_ReturnsUserGet_WhenSuccessful() {
        ResponseEntity<UserGet> userGetResponse = userController.findByEmail(EMAIL1);

        Assertions.assertThat(userGetResponse.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        Assertions.assertThat(userGetResponse.getBody()).isNotNull().isExactlyInstanceOf(UserGet.class);

        Assertions.assertThat(userGetResponse.getBody().getId()).isNotNull().isEqualTo(EXPECTED_ID);
        Assertions.assertThat(userGetResponse.getBody().getName()).isNotNull().isEqualTo(EXPECTED_NAME);
        Assertions.assertThat(userGetResponse.getBody().getEmail()).isNotNull().isEqualTo(EXPECTED_EMAIL);
    }

    @Test
    @DisplayName("findByEmail Throws NotFoundException When User Is Not Found")
    void findByEmail_ThrowsNotFoundException_WhenUserIsNotFound() {
        BDDMockito.when(userServiceMock.findByEmailOrThrowNotFoundException(ArgumentMatchers.anyString()))
                .thenThrow(new NotFoundException(USER_WITH_EMAIL_NOT_FOUND.formatted(EMAIL1)));

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> userController.findByEmail(EMAIL1))
                .withMessageContainingAll(USER_WITH_EMAIL_NOT_FOUND.formatted(EMAIL1));
    }

    @Test
    @DisplayName("findByName Returns a List Of UserGet When Successful")
    void findByName_ReturnsAListOfUsersGet_WhenSuccessful() {
        ResponseEntity<List<UserGet>> userGetResponseList = userController.findByName("NameTest");

        Assertions.assertThat(userGetResponseList.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        Assertions.assertThat(userGetResponseList.getBody()).isNotNull().hasSize(1);
        Assertions.assertThat(userGetResponseList.getBody().get(0)).isNotNull().isExactlyInstanceOf(UserGet.class);
        Assertions.assertThat(userGetResponseList.getBody().get(0).getId()).isNotNull().isEqualTo(EXPECTED_ID);
        Assertions.assertThat(userGetResponseList.getBody().get(0).getName()).isNotNull().isEqualTo(EXPECTED_NAME);
        Assertions.assertThat(userGetResponseList.getBody().get(0).getEmail()).isNotNull().isEqualTo(EXPECTED_EMAIL);
    }

    @Test
    @DisplayName("findByName Returns Empty List When User Is Not Found")
    void findByName_ReturnsEmptyList_WhenUserIsNotFound() {
        BDDMockito.when(userServiceMock.findByName(ArgumentMatchers.anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserGet>> userGetResponseList = userController.findByName("NameTest");

        Assertions.assertThat(userGetResponseList.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        Assertions.assertThat(userGetResponseList.getBody()).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save Returns Void When Successful")
    void save_ReturnsVoid_WhenSuccessful() {
        ResponseEntity<Void> voidResponse = userController.save(UserPostRequestBodyCreator.createUserPostRequestBody());

        Assertions.assertThat(voidResponse.getStatusCode()).isNotNull().isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(voidResponse.getBody()).isNull();
        Assertions.assertThat(voidResponse.getHeaders().getLocation().getPath()).isNotNull()
                .isEqualTo("/" + EXPECTED_ID);
    }

    @Test
    @DisplayName("save Throws EmailAlreadyExistsException When User With Email Already Exists")
    void save_ThrowsEmailAlreadyExistsException_WhenUserWithEmailAlreadyExists() {
        BDDMockito.when(userServiceMock.save(ArgumentMatchers.any(UserPostRequestBody.class)))
                .thenThrow(new EmailAlreadyExistsException(USER_WITH_EMAIL_ALREADY_EXISTS.formatted(EMAIL1)));

        Assertions.assertThatExceptionOfType(EmailAlreadyExistsException.class)
                .isThrownBy(() -> userController.save(UserPostRequestBodyCreator.createUserPostRequestBody()))
                .withMessageContainingAll(USER_WITH_EMAIL_ALREADY_EXISTS.formatted(EMAIL1));
    }

    @Test
    @DisplayName("update Returns Void When Successful")
    void update_ReturnsVoid_WhenSuccessful() {
        ResponseEntity<Void> voidResponse = userController.update(UserPutRequestBodyCreator.createUserPutRequestBody());

        Assertions.assertThat(voidResponse.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertThat(voidResponse.getBody()).isNull();
    }

    @Test
    @DisplayName("update Throws EmailAlreadyExistsException When User With Email Already Exists")
    void update_ThrowsEmailAlreadyExistsException_WhenUserWithEmailAlreadyExists() {
        BDDMockito.when(userServiceMock.update(ArgumentMatchers.any(UserPutRequestBody.class)))
                .thenThrow(new EmailAlreadyExistsException(USER_WITH_EMAIL_ALREADY_EXISTS.formatted(EMAIL1)));

        Assertions.assertThatExceptionOfType(EmailAlreadyExistsException.class)
                .isThrownBy(() -> userController.update(UserPutRequestBodyCreator.createUserPutRequestBody()))
                .withMessageContainingAll(USER_WITH_EMAIL_ALREADY_EXISTS.formatted(EMAIL1));
    }

    @Test
    @DisplayName("update Throws NotFoundException When User Is Not Found")
    void update_ThrowsNotFoundException_WhenUserIsNotFound() {
        BDDMockito.when(userServiceMock.update(ArgumentMatchers.any(UserPutRequestBody.class)))
                .thenThrow(new NotFoundException(USER_WITH_ID_NOT_FOUND.formatted(ID1)));

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> userController.update(UserPutRequestBodyCreator.createUserPutRequestBody()))
                .withMessageContainingAll(USER_WITH_ID_NOT_FOUND.formatted(ID1));
    }

    @Test
    @DisplayName("deleteById Remove User When Successful")
    void deleteById_RemoveUser_WhenSuccessful() {
        ResponseEntity<Void> voidResponse = userController.deleteById(ID1);

        Assertions.assertThat(voidResponse.hasBody()).isFalse();
        Assertions.assertThatCode(voidResponse::hasBody).doesNotThrowAnyException();
        Assertions.assertThat(voidResponse.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NO_CONTENT);

        Mockito.verify(userServiceMock, Mockito.times(1)).deleteById(ID1);
    }

    @Test
    @DisplayName("deleteById Throws NotFoundException When User Is Not Found")
    void deleteById_ThrowsNotFoundException_WhenUserIsNotFound() {
        BDDMockito.when(userController.deleteById(ArgumentMatchers.anyInt()))
                .thenThrow(new NotFoundException(USER_WITH_ID_NOT_FOUND.formatted(ID1)));

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> userController.deleteById(ID1))
                .withMessageContainingAll(USER_WITH_ID_NOT_FOUND.formatted(ID1));

        Mockito.verify(userServiceMock, Mockito.times(1)).deleteById(ID1);
    }
}