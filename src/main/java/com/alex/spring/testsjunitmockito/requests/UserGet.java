package com.alex.spring.testsjunitmockito.requests;

import com.alex.spring.testsjunitmockito.domain.User;
import lombok.Builder;

@Builder
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}