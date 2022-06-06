package com.alex.spring.testsjunitmockito.requests;

import com.alex.spring.testsjunitmockito.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class UserGet {

    private Integer id;
    private String name;
    private String email;

    public UserGet(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UserGet(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}