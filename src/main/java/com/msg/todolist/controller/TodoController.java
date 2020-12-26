package com.msg.todolist.controller;

import com.msg.todolist.model.TodoRequest;
import com.msg.todolist.model.TodoResponse;
import com.msg.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todo")
    public List<TodoResponse> getTodoItems(){
        return todoService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/todo")
    public void addTodo(@RequestBody @Valid TodoRequest todoRequest){
        todoService.addTodo(todoRequest);
    }

    @DeleteMapping("/todo/{todoId}")
    public void deleteTodo(@PathVariable Long todoId){
        todoService.deleteTodo(todoId);

    }

    @PutMapping("/todo/{todoId}")
    public void  toggleTodo(@PathVariable Long todoId){
        todoService.toggleTodo(todoId);
    }

}
