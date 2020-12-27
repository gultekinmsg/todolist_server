package com.msg.todolist.controller;

import com.msg.todolist.model.TodoRequest;
import com.msg.todolist.model.TodoResponse;
import com.msg.todolist.service.TodoService;
import com.msg.todolist.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TodoController {

    private final TodoService todoService;
    private final UserDetailService userDetailService;

    @Autowired
    public TodoController(TodoService todoService, UserDetailService userDetailService) {
        this.todoService = todoService;
        this.userDetailService = userDetailService;
    }

    @GetMapping("/todo")
    public List<TodoResponse> getTodoItems() {
        return todoService.findAll(userDetailService.findCurrentUser());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/todo")
    public void addTodo(@RequestBody @Valid TodoRequest todoRequest) {
        todoService.addTodo(todoRequest, userDetailService.findCurrentUser());
    }

    @DeleteMapping("/todo/{todoId}")
    public void deleteTodo(@PathVariable Long todoId) {
        todoService.deleteTodo(todoId, userDetailService.findCurrentUser());

    }

    @PutMapping("/todo/{todoId}")
    public void toggleTodo(@PathVariable Long todoId) {
        todoService.toggleTodo(todoId, userDetailService.findCurrentUser());
    }
}
