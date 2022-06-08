package com.alex.spring.junitmockito.services;

import com.alex.spring.junitmockito.domain.User;
import com.alex.spring.junitmockito.exceptions.EmailAlreadyExistsException;
import com.alex.spring.junitmockito.exceptions.NotFoundException;
import com.alex.spring.junitmockito.repositories.UserRepository;
import com.alex.spring.junitmockito.requests.UserPostRequestBody;
import com.alex.spring.junitmockito.requests.UserPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByIdOrThrowNotFoundException(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id `%d` not found".formatted(id)));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByEmailOrThrowNotFoundException(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email `%s` not found".formatted(email)));
    }

    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public User save(UserPostRequestBody userPostRequestBody) {
        checkingEmailAlreadyExists(userPostRequestBody.getEmail());
        return userRepository.save(User.builder()
                .name(userPostRequestBody.getName())
                .email(userPostRequestBody.getEmail())
                .password(userPostRequestBody.getPassword())
                .build());
    }

    public User update(UserPutRequestBody userPutRequestBody) {
        findByIdOrThrowNotFoundException(userPutRequestBody.getId());
        checkingEmailAlreadyExists(userPutRequestBody);
        return userRepository.save(User.builder()
                .id(userPutRequestBody.getId())
                .name(userPutRequestBody.getName())
                .email(userPutRequestBody.getEmail())
                .password(userPutRequestBody.getPassword())
                .build());
    }

    public void deleteById(Integer id) {
        findByIdOrThrowNotFoundException(id);
        userRepository.deleteById(id);
    }

    private void checkingEmailAlreadyExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("User with email `%s` Already Exists!".formatted(email));
        }
    }

    private void checkingEmailAlreadyExists(UserPutRequestBody user) {
        if (userRepository.findByEmail(user.getEmail()).map(User::getId).filter(i -> !user.getId().equals(i)).isPresent()) {
            throw new EmailAlreadyExistsException("User with email `%s` Already Exists!".formatted(user.getEmail()));
        }
    }
}