package com.msg.todolist.service;

import com.msg.todolist.entity.Todo;
import com.msg.todolist.model.TodoRequest;
import com.msg.todolist.model.TodoResponse;
import com.msg.todolist.repository.TodoRepository;
import com.msg.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserDetailService userDetailService;

    @Autowired
    public TodoService(TodoRepository todoRepository, UserRepository userRepository, UserDetailService userDetailService) {
        this.todoRepository = todoRepository;
        this.userDetailService = userDetailService;
    }

    public List<TodoResponse> findAll() {
        if (todoRepository.findAllByUserIdOrderByDateTimeDesc(userDetailService.findCurrentUser().getId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "empty todo list");
        }
        List<TodoResponse> todoResponseList = new ArrayList<>();
        List<Todo> items = todoRepository.findAllByUserIdOrderByDateTimeDesc(userDetailService.findCurrentUser().getId());
        for (Todo item : items) {
                TodoResponse model = new TodoResponse();
                model.setId(item.getId());
                model.setName(item.getName());
                model.setCompleted(item.getCompleted());
                model.setDateTime(item.getDateTime());
                model.setUserId(item.getUser().getId());
                todoResponseList.add(model);
        }
        return todoResponseList;
    }

    public void addTodo(TodoRequest todoRequest) {
        Todo todo = new Todo();
        todo.setName(todoRequest.getName());
        todo.setCompleted(todoRequest.getCompleted());
        todo.setDateTime(LocalDateTime.now());
        todo.setUser(userDetailService.findCurrentUser());
        todoRepository.save(todo);
    }

    public void deleteTodo(Long todoId) {
        Todo todoToChange = todoRepository.findByIdAndUserId(todoId,userDetailService.findCurrentUser().getId());
        todoRepository.delete(todoToChange);

    }

    public void toggleTodo(Long todoId) {
        Todo todoToChange = todoRepository.findByIdAndUserId(todoId,userDetailService.findCurrentUser().getId());
        todoToChange.setCompleted(!todoToChange.getCompleted());
        todoRepository.save(todoToChange);
    }

}
