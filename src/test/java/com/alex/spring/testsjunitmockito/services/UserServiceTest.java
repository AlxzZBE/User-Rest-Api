package com.alex.spring.testsjunitmockito.services;

import com.alex.spring.testsjunitmockito.domain.User;
import com.alex.spring.testsjunitmockito.repositories.UserRepository;
import com.alex.spring.testsjunitmockito.util.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
@ExtendWith(SpringExtension.class)
class UserServiceTest {

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
    @DisplayName("findByIdOrThrowNotFoundException returns User when successful")
    void findById_ReturnsUser_WhenSuccessful() {
        Integer expectedId = UserCreator.createValidUser().getId();
        String expectedName = UserCreator.createValidUser().getName();
        String expectedEmail = UserCreator.createValidUser().getEmail();
        String expectedPassword = UserCreator.createValidUser().getPassword();
        User userResponse = userService.findByIdOrThrowNotFoundException(1);

        Assertions.assertThat(userResponse).isNotNull().isExactlyInstanceOf(User.class);
        Assertions.assertThat(userResponse.getId()).isNotNull().isEqualTo(expectedId);
        Assertions.assertThat(userResponse.getName()).isNotNull().isEqualTo(expectedName);
        Assertions.assertThat(userResponse.getEmail()).isNotNull().isEqualTo(expectedEmail);
        Assertions.assertThat(userResponse.getPassword()).isNotNull().isEqualTo(expectedPassword);
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