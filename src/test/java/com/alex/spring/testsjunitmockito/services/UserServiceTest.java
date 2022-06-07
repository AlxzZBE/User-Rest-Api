package com.alex.spring.testsjunitmockito.services;

import com.alex.spring.testsjunitmockito.domain.User;
import com.alex.spring.testsjunitmockito.exceptions.NotFoundException;
import com.alex.spring.testsjunitmockito.repositories.UserRepository;
import com.alex.spring.testsjunitmockito.util.UserCreator;
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

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    public static final int ID1 = 1;
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepositoryMock;

    @BeforeEach
    void setUp() {
        BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(UserCreator.createValidUser()));
    }

    @Test
    @DisplayName("findByIdOrThrowNotFoundException Returns User When Successful")
    void findById_ReturnsUser_WhenSuccessful() {
        Integer expectedId = UserCreator.createValidUser().getId();
        String expectedName = UserCreator.createValidUser().getName();
        String expectedEmail = UserCreator.createValidUser().getEmail();
        String expectedPassword = UserCreator.createValidUser().getPassword();
        User userResponse = userService.findByIdOrThrowNotFoundException(ID1);

        Assertions.assertThat(userResponse).isNotNull().isExactlyInstanceOf(User.class);
        Assertions.assertThat(userResponse.getId()).isNotNull().isEqualTo(expectedId);
        Assertions.assertThat(userResponse.getName()).isNotNull().isEqualTo(expectedName);
        Assertions.assertThat(userResponse.getEmail()).isNotNull().isEqualTo(expectedEmail);
        Assertions.assertThat(userResponse.getPassword()).isNotNull().isEqualTo(expectedPassword);
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
    void findAll() {
    }

    @Test
    void findByEmailOrThrowNotFoundException() {
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