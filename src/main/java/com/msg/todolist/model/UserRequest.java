package com.msg.todolist.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserRequest {
    @NotBlank
    @Size(max = 255)
    private String userName;
    @NotBlank
    @Size(max = 255)
    private String password;
}
