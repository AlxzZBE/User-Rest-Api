package com.alex.spring.testsjunitmockito.controller;

import com.alex.spring.testsjunitmockito.domain.User;
import com.alex.spring.testsjunitmockito.requests.UserGet;
import com.alex.spring.testsjunitmockito.requests.UserPostRequestBody;
import com.alex.spring.testsjunitmockito.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserGet> findById(@PathVariable Integer id) {
        log.info("Finding User by id `{}`", id);
        User userSaved = userService.findById(id);
        return ResponseEntity.ok(UserGet.builder()
                .id(userSaved.getId())
                .name(userSaved.getName())
                .email(userSaved.getEmail()).build());
    }

    @GetMapping
    public ResponseEntity<List<UserGet>> findAll() {
        log.info("Finding All Users");
        return ResponseEntity.ok(userService.findAll().stream().map(UserGet::new).toList());
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody UserPostRequestBody userPostRequestBody) {
        log.info("Saving User `{}`", userPostRequestBody.getName());
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(userService.save(userPostRequestBody).getId()).toUri()).build();
    }
}