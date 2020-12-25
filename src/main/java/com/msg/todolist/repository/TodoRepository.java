package com.msg.todolist.repository;

import com.msg.todolist.entity.Todo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo,Long>{
    List<Todo> findAll();
    List<Todo> findAllByOrderByDateTimeDesc();

}
