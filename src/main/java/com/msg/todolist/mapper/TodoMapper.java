package com.msg.todolist.mapper;

import com.msg.todolist.entity.Todo;
import com.msg.todolist.entity.User;
import com.msg.todolist.model.TodoRequest;
import com.msg.todolist.model.TodoResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TodoMapper {

    public static List<TodoResponse> toModels(List<Todo> entities) {
        List<TodoResponse> models = new ArrayList<>();
        entities.forEach(entity -> models.add(toModel(entity)));
        return models;
    }

    private static TodoResponse toModel(Todo entity) {
        TodoResponse model = new TodoResponse();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setCompleted(entity.getCompleted());
        model.setDateTime(entity.getDateTime());
        model.setUserId(entity.getUser().getId());
        return model;

    }

    public static Todo toEntity(TodoRequest model, User user) {
        Todo todo = new Todo();
        todo.setName(model.getName());
        todo.setCompleted(model.getCompleted());
        todo.setDateTime(LocalDateTime.now());
        todo.setUser(user);
        return todo;
    }
}
