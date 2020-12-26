package com.msg.todolist.model;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UserResponse {
    private Long id;
    @Email
    private String userName;
}
