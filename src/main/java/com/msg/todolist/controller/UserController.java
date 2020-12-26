package com.msg.todolist.controller;

import com.msg.todolist.model.UserRequest;
import com.msg.todolist.model.UserResponse;
import com.msg.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers(){
        return userService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user")
    public void addUser(@RequestBody @Valid UserRequest userRequest){
        userService.addUser(userRequest);
    }

    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }




}
