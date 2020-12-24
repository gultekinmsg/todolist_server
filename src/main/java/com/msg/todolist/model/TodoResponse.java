package com.msg.todolist.model;

import lombok.Data;

@Data
public class TodoResponse {
    private Long id;
    private String name;
    private Boolean completed;
}
