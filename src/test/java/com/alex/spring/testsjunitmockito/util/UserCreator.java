package com.alex.spring.testsjunitmockito.util;

import com.alex.spring.testsjunitmockito.domain.User;

public class UserCreator {

    public static final int ID = 2;
    public static final String NAME = "Alex";
    public static final String EMAIL = "alex123@gmail.com";
    public static final String PASSWORD = "123abc";
    public static final String NAMEUPDATED = "Alex Updated";
    public static final String EMAILUPDATED = "alex123Updated@gmail.com";
    public static final String PASSWORDUPDATED = "123abcupdated";

    public static User createValidUser() {
        return User.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    public static User createUserToBeSaved() {
        return User.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    public static User createValidUpdatedUser() {
        return User.builder()
                .id(ID)
                .name(NAMEUPDATED)
                .email(EMAILUPDATED)
                .password(PASSWORDUPDATED)
                .build();
    }
}
