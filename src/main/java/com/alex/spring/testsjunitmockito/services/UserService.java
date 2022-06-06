package com.alex.spring.testsjunitmockito.services;

import com.alex.spring.testsjunitmockito.domain.User;
import com.alex.spring.testsjunitmockito.exceptions.EmailAlreadyExistsException;
import com.alex.spring.testsjunitmockito.exceptions.NotFoundException;
import com.alex.spring.testsjunitmockito.repositories.UserRepository;
import com.alex.spring.testsjunitmockito.requests.UserPostRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id `%d` not found".formatted(id)));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email `%s` not found".formatted(email)));
    }

    public User save(UserPostRequestBody userPostRequestBody) {
        checkingEmailAlreadyExists(userPostRequestBody.getEmail());
        return userRepository.save(User.builder()
                .name(userPostRequestBody.getName())
                .email(userPostRequestBody.getEmail())
                .password(userPostRequestBody.getPassword())
                .build());
    }

    private void checkingEmailAlreadyExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("User with email `%s` Already Exists!".formatted(email));
        }
    }
}
