package com.alex.spring.testsjunitmockito.services;

import com.alex.spring.testsjunitmockito.domain.User;
import com.alex.spring.testsjunitmockito.exceptions.EmailAlreadyExistsException;
import com.alex.spring.testsjunitmockito.exceptions.NotFoundException;
import com.alex.spring.testsjunitmockito.repositories.UserRepository;
import com.alex.spring.testsjunitmockito.requests.UserPutRequestBody;
import com.alex.spring.testsjunitmockito.util.UserCreator;
import com.alex.spring.testsjunitmockito.util.UserPostRequestBodyCreator;
import com.alex.spring.testsjunitmockito.util.UserPutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    public static final int ID1 = 1;
    public static final String EMAIL1 = "email1@gmail.com";
    public static final String EXPECTED_PASSWORD = UserCreator.createValidUser().getPassword();
    public static final String EXPECTED_EMAIL = UserCreator.createValidUser().getEmail();
    public static final String EXPECTED_NAME = UserCreator.createValidUser().getName();
    public static final Integer EXPECTED_ID = UserCreator.createValidUser().getId();
    public static final String USER_WITH_ID_NOT_FOUND = "User with id `%d` not found";
    public static final String USER_WITH_EMAIL_NOT_FOUND = "User with email `%s` not found";
    public static final String USER_WITH_EMAIL_ALREADY_EXISTS = "User with email `%s` Already Exists!";
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepositoryMock;

    @BeforeEach
    void setUp() {
        BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(UserCreator.createValidUser()));

        BDDMockito.when(userRepositoryMock.findAll()).thenReturn(List.of(UserCreator.createValidUser()));

        BDDMockito.when(userRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(UserCreator.createValidUser()));

        BDDMockito.when(userRepositoryMock.save(ArgumentMatchers.any(User.class)))
                .thenReturn(UserCreator.createValidUser());

        BDDMockito.doNothing().when(userRepositoryMock).deleteById(ArgumentMatchers.anyInt());
    }

    @Test
    @DisplayName("findByIdOrThrowNotFoundException Returns User When Successful")
    void findById_ReturnsUser_WhenSuccessful() {
        User userResponse = userService.findByIdOrThrowNotFoundException(ID1);

        Assertions.assertThat(userResponse).isNotNull().isExactlyInstanceOf(User.class);
        Assertions.assertThat(userResponse.getId()).isNotNull().isEqualTo(EXPECTED_ID);
        Assertions.assertThat(userResponse.getName()).isNotNull().isEqualTo(EXPECTED_NAME);
        Assertions.assertThat(userResponse.getEmail()).isNotNull().isEqualTo(EXPECTED_EMAIL);
        Assertions.assertThat(userResponse.getPassword()).isNotNull().isEqualTo(EXPECTED_PASSWORD);
    }

    @Test
    @DisplayName("findByIdOrThrowNotFoundException throws NotFoundException When User Is Not Found")
    void findById_ThrowsNotFoundException_WhenUserIsNotFound() {
        BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenThrow(new NotFoundException(USER_WITH_ID_NOT_FOUND.formatted(ID1)));

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> userService.findByIdOrThrowNotFoundException(ID1))
                .withMessageContainingAll(USER_WITH_ID_NOT_FOUND.formatted(ID1));
    }

    @Test
    @DisplayName("findAll Returns a List of User When Successful")
    void findAll_ReturnsAListOfUser_WhenSuccessful() {
        List<User> userResponseList = userService.findAll();

        Assertions.assertThat(userResponseList).isNotEmpty().isNotNull().hasSize(1);
        Assertions.assertThat(userResponseList.get(0)).isNotNull().isExactlyInstanceOf(User.class);
        Assertions.assertThat(userResponseList.get(0).getId()).isNotNull().isEqualTo(EXPECTED_ID);
        Assertions.assertThat(userResponseList.get(0).getName()).isNotNull().isEqualTo(EXPECTED_NAME);
        Assertions.assertThat(userResponseList.get(0).getEmail()).isNotNull().isEqualTo(EXPECTED_EMAIL);
        Assertions.assertThat(userResponseList.get(0).getPassword()).isNotNull().isEqualTo(EXPECTED_PASSWORD);
    }

    @Test
    @DisplayName("findAll Returns a Empty List When User is Not Found")
    void findAll_ReturnsAEmptyList_WhenUserIsNotFound() {
        BDDMockito.when(userRepositoryMock.findAll()).thenReturn(Collections.emptyList());
        List<User> userResponseList = userService.findAll();
        Assertions.assertThat(userResponseList).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findByEmailOrThrowNotFoundException Returns User When Successful")
    void findByEmail_ReturnsUser_WhenSuccessful() {
        BDDMockito.when(userRepositoryMock.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(UserCreator.createValidUser()));

        User userResponse = userService.findByEmailOrThrowNotFoundException(EMAIL1);

        Assertions.assertThat(userResponse).isNotNull().isExactlyInstanceOf(User.class);
        Assertions.assertThat(userResponse.getId()).isNotNull().isEqualTo(EXPECTED_ID);
        Assertions.assertThat(userResponse.getName()).isNotNull().isEqualTo(EXPECTED_NAME);
        Assertions.assertThat(userResponse.getEmail()).isNotNull().isEqualTo(EXPECTED_EMAIL);
        Assertions.assertThat(userResponse.getPassword()).isNotNull().isEqualTo(EXPECTED_PASSWORD);
    }

    @Test
    @DisplayName("findByEmailOrThrowNotFoundException throws NotFoundException When User Is Not Found")
    void findByEmail_ThrowsNotFoundException_WhenUserIsNotFound() {
        BDDMockito.when(userRepositoryMock.findByEmail(ArgumentMatchers.anyString()))
                .thenThrow(new NotFoundException(USER_WITH_EMAIL_NOT_FOUND.formatted(EMAIL1)));

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> userService.findByEmailOrThrowNotFoundException(EMAIL1))
                .withMessageContainingAll(USER_WITH_EMAIL_NOT_FOUND.formatted(EMAIL1));
    }

    @Test
    @DisplayName("findByName Returns a List of User When Successful")
    void findByName_ReturnsAListOfUser_WhenSuccessful() {
        List<User> userResponseList = userService.findByName("NameTest");

        Assertions.assertThat(userResponseList).isNotEmpty().isNotNull().hasSize(1);
        Assertions.assertThat(userResponseList.get(0)).isNotNull().isExactlyInstanceOf(User.class);
        Assertions.assertThat(userResponseList.get(0).getId()).isNotNull().isEqualTo(EXPECTED_ID);
        Assertions.assertThat(userResponseList.get(0).getName()).isNotNull().isEqualTo(EXPECTED_NAME);
        Assertions.assertThat(userResponseList.get(0).getEmail()).isNotNull().isEqualTo(EXPECTED_EMAIL);
        Assertions.assertThat(userResponseList.get(0).getPassword()).isNotNull().isEqualTo(EXPECTED_PASSWORD);
    }

    @Test
    @DisplayName("findByName Returns a Empty List When User is Not Found")
    void findByName_ReturnsAEmptyList_WhenUserIsNotFound() {
        BDDMockito.when(userRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<User> userResponseList = userService.findByName("NameTest");
        Assertions.assertThat(userResponseList).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save Returns User When Successful")
    void save_ReturnsUser_WhenSuccessful() {
        User userResponse = userService.save(UserPostRequestBodyCreator.createUserPostRequestBody());

        Assertions.assertThat(userResponse).isNotNull().isExactlyInstanceOf(User.class);
        Assertions.assertThat(userResponse.getId()).isNotNull().isEqualTo(EXPECTED_ID);
        Assertions.assertThat(userResponse.getName()).isNotNull().isEqualTo(EXPECTED_NAME);
        Assertions.assertThat(userResponse.getEmail()).isNotNull().isEqualTo(EXPECTED_EMAIL);
        Assertions.assertThat(userResponse.getPassword()).isNotNull().isEqualTo(EXPECTED_PASSWORD);
    }

    @Test
    @DisplayName("save Throws EmailAlreadyExistsException When User with email Already Exists")
    void save_ThrowsEmailAlreadyExistsException_WhenUserWithEmailAlreadyExists() {
        BDDMockito.when(userRepositoryMock.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(UserCreator.createValidUser()));

        Assertions.assertThatExceptionOfType(EmailAlreadyExistsException.class)
                .isThrownBy(() -> userService.save(UserPostRequestBodyCreator.createUserPostRequestBody()))
                .withMessageContainingAll(USER_WITH_EMAIL_ALREADY_EXISTS.formatted(EXPECTED_EMAIL));
    }

    @Test
    void update() {
        Integer expectedIdUpdated = UserCreator.createValidUpdatedUser().getId();
        String expectedNameUpdated = UserCreator.createValidUpdatedUser().getName();
        String expectedEmailUpdated = UserCreator.createValidUpdatedUser().getEmail();
        String expectedPasswordUpdated = UserCreator.createValidUpdatedUser().getPassword();

        BDDMockito.when(userRepositoryMock.save(ArgumentMatchers.any(User.class)))
                .thenReturn(UserCreator.createValidUpdatedUser());

        User userResponse = userService.update(UserPutRequestBodyCreator.createUserPutRequestBody());

        Assertions.assertThat(userResponse).isNotNull().isExactlyInstanceOf(User.class);
        Assertions.assertThat(userResponse.getId()).isNotNull().isEqualTo(expectedIdUpdated);
        Assertions.assertThat(userResponse.getName()).isNotNull().isEqualTo(expectedNameUpdated);
        Assertions.assertThat(userResponse.getEmail()).isNotNull().isEqualTo(expectedEmailUpdated);
        Assertions.assertThat(userResponse.getPassword()).isNotNull().isEqualTo(expectedPasswordUpdated);
    }

    @Test
    @DisplayName("update Throws EmailAlreadyExistsException When User with email Already Exists")
    void update_ThrowsEmailAlreadyExistsException_WhenUserWithEmailAlreadyExists() {
        BDDMockito.when(userRepositoryMock.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(UserCreator.createValidUser()));

        UserPutRequestBody userPutRequestBody = UserPutRequestBodyCreator.createUserPutRequestBody();
        userPutRequestBody.setId(3);

        Assertions.assertThatExceptionOfType(EmailAlreadyExistsException.class)
                .isThrownBy(() -> userService.update(userPutRequestBody))
                .withMessageContainingAll(USER_WITH_EMAIL_ALREADY_EXISTS.formatted(userPutRequestBody.getEmail()));

//        Mockito.verify(userRepositoryMock, Mockito.times(0)).save(UserCreator.createValidUser());
    }

    @Test
    @DisplayName("deleteById Remove User When Successful")
    void deleteById_RemoveUser_WhenSuccessful() {
        Assertions.assertThatCode(() -> userService.deleteById(ID1)).doesNotThrowAnyException();
        Mockito.verify(userRepositoryMock, Mockito.times(1)).deleteById(ID1);
    }

    @Test
    @DisplayName("deleteById Throws NotFoundException When User Is Not Found")
    void deleteById_ThrowsNotFoundException_WhenUserIsNotFound() {
        BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenThrow(new NotFoundException(USER_WITH_ID_NOT_FOUND.formatted(ID1)));

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> userService.deleteById(ID1))
                .withMessageContainingAll(USER_WITH_ID_NOT_FOUND.formatted(ID1));

        Mockito.verify(userRepositoryMock, Mockito.times(0)).deleteById(ID1);
    }
}