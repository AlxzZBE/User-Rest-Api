package com.alex.spring.testsjunitmockito.controller;

import com.alex.spring.testsjunitmockito.domain.User;
import com.alex.spring.testsjunitmockito.requests.UserGet;
import com.alex.spring.testsjunitmockito.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserGet> findById(@PathVariable Integer id) {
        User userSaved = userService.findById(id);
        return ResponseEntity.ok(UserGet.builder().name(userSaved.getName()).email(userSaved.getEmail()).build());
    }
}
