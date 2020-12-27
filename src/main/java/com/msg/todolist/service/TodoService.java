package com.msg.todolist.service;

import com.msg.todolist.entity.Todo;
import com.msg.todolist.entity.User;
import com.msg.todolist.mapper.TodoMapper;
import com.msg.todolist.model.TodoRequest;
import com.msg.todolist.model.TodoResponse;
import com.msg.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoResponse> findAll(User user) {
        List<Todo> items = todoRepository.findAllByUserIdOrderByDateTimeDesc(user.getId());
        if (items.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "empty todo list");
        }
        return TodoMapper.toModels(items);
    }


    public void addTodo(TodoRequest todoRequest, User user) {
        Todo newTodo = TodoMapper.toEntity(todoRequest, user);
        todoRepository.save(newTodo);
    }

    public void deleteTodo(Long todoId, User user) {
        Todo todoToDelete = todoRepository.findByIdAndUserId(todoId, user.getId());
        validateTodo(todoToDelete);
        todoRepository.delete(todoToDelete);

    }

    public void toggleTodo(Long todoId, User user) {
        Todo todoToChange = todoRepository.findByIdAndUserId(todoId, user.getId());
        validateTodo(todoToChange);
        todoToChange.setCompleted(!todoToChange.getCompleted());
        todoRepository.save(todoToChange);
    }

    private void validateTodo(Todo todoToChange) {
        if (todoToChange == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not exist");
        }
    }


}
