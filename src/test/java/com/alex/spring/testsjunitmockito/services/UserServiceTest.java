package com.alex.spring.testsjunitmockito.services;

import com.alex.spring.testsjunitmockito.domain.User;
import com.alex.spring.testsjunitmockito.exceptions.NotFoundException;
import com.alex.spring.testsjunitmockito.repositories.UserRepository;
import com.alex.spring.testsjunitmockito.util.UserCreator;
import com.alex.spring.testsjunitmockito.util.UserPostRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepositoryMock;

    @BeforeEach
    void setUp() {
        BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(UserCreator.createValidUser()));

        BDDMockito.when(userRepositoryMock.findAll()).thenReturn(List.of(UserCreator.createValidUser()));

        BDDMockito.when(userRepositoryMock.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(UserCreator.createValidUser()));
        
        BDDMockito.when(userRepositoryMock.save(ArgumentMatchers.any(User.class)))
                .thenReturn(UserCreator.createValidUser());
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
                .thenThrow(new NotFoundException("User with id `%d` not found".formatted(ID1)));

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> userService.findByIdOrThrowNotFoundException(ID1))
                .withMessageContainingAll("User with id `%d` not found".formatted(ID1));
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
    void findByEmailOrThrowNotFoundException() {
        User userResponse = userService.findByEmailOrThrowNotFoundException(EMAIL1);

        Assertions.assertThat(userResponse).isNotNull().isExactlyInstanceOf(User.class);
        Assertions.assertThat(userResponse.getId()).isNotNull().isEqualTo(EXPECTED_ID);
        Assertions.assertThat(userResponse.getName()).isNotNull().isEqualTo(EXPECTED_NAME);
        Assertions.assertThat(userResponse.getEmail()).isNotNull().isEqualTo(EXPECTED_EMAIL);
        Assertions.assertThat(userResponse.getPassword()).isNotNull().isEqualTo(EXPECTED_PASSWORD);
    }

    @Test
    void findByName() {
    }

    @Test
    void save() {
        User userResponse = userService.save(UserPostRequestBodyCreator.createUserPostRequestBody());

        Assertions.assertThat(userResponse).isNotNull().isExactlyInstanceOf(User.class);
        Assertions.assertThat(userResponse.getId()).isNotNull().isEqualTo(EXPECTED_ID);
        Assertions.assertThat(userResponse.getName()).isNotNull().isEqualTo(EXPECTED_NAME);
        Assertions.assertThat(userResponse.getEmail()).isNotNull().isEqualTo(EXPECTED_EMAIL);
        Assertions.assertThat(userResponse.getPassword()).isNotNull().isEqualTo(EXPECTED_PASSWORD);
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}