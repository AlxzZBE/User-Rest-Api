package com.alex.spring.junitmockito.util;

import com.alex.spring.junitmockito.requests.UserPutRequestBody;

public class UserPutRequestBodyCreator {

    public static UserPutRequestBody createUserPutRequestBody() {
        return UserPutRequestBody.builder()
                .id(UserCreator.createValidUpdatedUser().getId())
                .name(UserCreator.createValidUpdatedUser().getName())
                .email(UserCreator.createValidUpdatedUser().getEmail())
                .password(UserCreator.createValidUpdatedUser().getPassword())
                .build();
    }
}