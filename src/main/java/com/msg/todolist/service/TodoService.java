package com.msg.todolist.service;

import com.msg.todolist.entity.Todo;
import com.msg.todolist.model.TodoRequest;
import com.msg.todolist.model.TodoResponse;
import com.msg.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoResponse> findAll() {
        if (todoRepository.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "empty list");
        }
        List<TodoResponse> todoResponseList = new ArrayList<>();
        List<Todo> items = todoRepository.findAll();
        for (Todo item : items) {
            TodoResponse model = new TodoResponse();
            model.setId(item.getId());
            model.setName(item.getName());
            model.setCompleted(item.getCompleted());
            todoResponseList.add(model);
        }
        return todoResponseList;
    }

    public void addTodo(TodoRequest todoRequest) {
        Todo todo = new Todo();
        todo.setName(todoRequest.getName());
        todo.setCompleted(todoRequest.getCompleted());
        todoRepository.save(todo);
    }

    public void deleteTodo(Long todoId) {
        checkTodoExists(todoId);
        todoRepository.deleteById(todoId);
    }

    public void toggleTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "item not exist"));
        todo.setCompleted(!todo.getCompleted());
        todoRepository.save(todo);
    }

    private void checkTodoExists(Long todoId) {
        if (todoRepository.findById(todoId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not exist");
        }
    }

}
