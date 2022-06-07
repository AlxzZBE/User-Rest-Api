package com.alex.spring.testsjunitmockito.util;

import com.alex.spring.testsjunitmockito.requests.UserGet;

public class UserGetCreator {

    public static UserGet createValidUserGet() {
        return UserGet.builder()
                .id(UserCreator.createValidUser().getId())
                .name(UserCreator.createValidUser().getName())
                .email(UserCreator.createValidUser().getEmail())
                .build();
    }
}
