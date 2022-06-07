package com.alex.spring.testsjunitmockito.controller;

import com.alex.spring.testsjunitmockito.domain.User;
import com.alex.spring.testsjunitmockito.requests.UserGet;
import com.alex.spring.testsjunitmockito.requests.UserPostRequestBody;
import com.alex.spring.testsjunitmockito.requests.UserPutRequestBody;
import com.alex.spring.testsjunitmockito.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserGet> findById(@PathVariable Integer id) {
        log.info("Finding User by Id `{}`", id);
        User userSaved = userService.findByIdOrThrowNotFoundException(id);
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

    @GetMapping(params = "email")
    public ResponseEntity<UserGet> findByEmail(@RequestParam String email) {
        log.info("Finding User by Email `{}`", email);
        return ResponseEntity.ok(new UserGet(userService.findByEmailOrThrowNotFoundException(email)));
    }

    @GetMapping(params = {"name"})
    public ResponseEntity<List<UserGet>> findByName(@RequestParam String name) {
        log.info("Finding Users by Name `{}`", name);
        return ResponseEntity.ok(userService.findByName(name).stream().map(UserGet::new).toList());
    }


    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid UserPostRequestBody userPostRequestBody) {
        log.info("Saving User `{}`", userPostRequestBody.getName());
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(userService.save(userPostRequestBody).getId()).toUri()).build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid UserPutRequestBody userPutRequestBody) {
        log.info("Updating User with id `{}`", userPutRequestBody.getId());
        userService.update(userPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}