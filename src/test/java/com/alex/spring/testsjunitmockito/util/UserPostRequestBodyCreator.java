package com.alex.spring.testsjunitmockito.util;

import com.alex.spring.testsjunitmockito.requests.UserPostRequestBody;

public class UserPostRequestBodyCreator {

    public static UserPostRequestBody createUserPostRequestBody() {
        return UserPostRequestBody.builder()
                .name(UserCreator.NAME)
                .email(UserCreator.EMAIL)
                .password(UserCreator.PASSWORD)
                .build();
    }
}
