package com.msg.todolist.service;

import com.msg.todolist.entity.Todo;
import com.msg.todolist.entity.User;
import com.msg.todolist.model.TodoRequest;
import com.msg.todolist.model.TodoResponse;
import com.msg.todolist.repository.TodoRepository;
import com.msg.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final UserDetailService userDetailService;

    @Autowired
    public TodoService(TodoRepository todoRepository, UserRepository userRepository, UserDetailService userDetailService) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
        this.userDetailService = userDetailService;
    }

    public List<TodoResponse> findAll() {
        if (todoRepository.findAllByOrderByDateTimeDesc().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "empty todo list");
        }
        List<TodoResponse> todoResponseList = new ArrayList<>();
        List<Todo> items = todoRepository.findAllByOrderByDateTimeDesc();
        for (Todo item : items) {
            if (item.getUser().getId().equals(findCurrentUser().getId())){
                TodoResponse model = new TodoResponse();
                model.setId(item.getId());
                model.setName(item.getName());
                model.setCompleted(item.getCompleted());
                model.setDateTime(item.getDateTime());
                model.setUserId(item.getUser().getId());
                todoResponseList.add(model);
            }
        }
        return todoResponseList;
    }

    public void addTodo(TodoRequest todoRequest) {
        Todo todo = new Todo();
        todo.setName(todoRequest.getName());
        todo.setCompleted(todoRequest.getCompleted());
        todo.setDateTime(LocalDateTime.now());
        todo.setUser(findCurrentUser());
        todoRepository.save(todo);
    }

    public void deleteTodo(Long todoId) {
        Optional<Todo> todos = todoRepository.findById(todoId);
        if (todos.get().getUser().getId().equals(findCurrentUser().getId())){
            checkTodoExists(todoId);
            todoRepository.deleteById(todoId);
        }else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authorized");
        }
    }

    public void toggleTodo(Long todoId) {
        Optional<Todo> todos = todoRepository.findById(todoId);
        if (todos.get().getUser().getId().equals(findCurrentUser().getId())){
            Todo todo = todoRepository.findById(todoId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "item not exist"));
            todo.setCompleted(!todo.getCompleted());
            todoRepository.save(todo);
        }else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authorized");
        }

    }

    private void checkTodoExists(Long todoId) {
        if (todoRepository.findById(todoId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not exist");
        }
    }

    public User findCurrentUser(){
        String currentUserName =userDetailService.getCurrentUsername();
        return userRepository.findByUserName(currentUserName);
    }

}
