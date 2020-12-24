package com.msg.todolist.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class TodoRequest {
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9\\s]+$")
    @Size(max = 255)
    private String name;
    @NotNull
    private Boolean completed;
}
