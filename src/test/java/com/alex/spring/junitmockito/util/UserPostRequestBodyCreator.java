package com.alex.spring.junitmockito.util;

import com.alex.spring.junitmockito.requests.UserPostRequestBody;

public class UserPostRequestBodyCreator {

    public static UserPostRequestBody createUserPostRequestBody() {
        return UserPostRequestBody.builder()
                .name(UserCreator.createUserToBeSaved().getName())
                .email(UserCreator.createUserToBeSaved().getEmail())
                .password(UserCreator.createUserToBeSaved().getPassword())
                .build();
    }
}
