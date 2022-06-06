package com.alex.spring.testsjunitmockito.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Builder
@Getter @Setter
public class UserPostRequestBody {

    @NotEmpty(message = "The `name` cannot be Empty or Null")
    @Length(min = 3, message = "The minimum number of characters in the `name` is 3")
    private String name;
    @Email
    private String email;
    @NotEmpty(message = "The `password` cannot be Empty or Null")
    @Length(min = 5, message = "The minimum number of characters in the `password` is 5")
    private String password;

    public UserPostRequestBody(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
