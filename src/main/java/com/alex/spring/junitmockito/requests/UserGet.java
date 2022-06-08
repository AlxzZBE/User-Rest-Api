package com.alex.spring.junitmockito.requests;

import com.alex.spring.junitmockito.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserGet {

    private Integer id;
    private String name;
    private String email;

    public UserGet(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}