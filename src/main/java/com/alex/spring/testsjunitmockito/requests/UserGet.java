package com.alex.spring.testsjunitmockito.requests;

import lombok.Builder;

@Builder
public class UserGet {

    private String id;
    private String name;
    private String email;
}
