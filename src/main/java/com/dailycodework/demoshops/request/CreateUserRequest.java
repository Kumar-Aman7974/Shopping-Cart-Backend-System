package com.dailycodework.demoshops.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {

    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
