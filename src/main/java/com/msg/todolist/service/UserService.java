package com.msg.todolist.service;

import com.msg.todolist.entity.User;
import com.msg.todolist.model.UserRequest;
import com.msg.todolist.model.UserResponse;
import com.msg.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> findAll(){
        if (userRepository.findAll().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"empty user list");
        }
        List<UserResponse> userResponseList = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            UserResponse model = new UserResponse();
            model.setId(user.getId());
            model.setUserName(user.getUserName());
            userResponseList.add(model);
        }
        return userResponseList;
    }

    public void addUser(UserRequest userRequest){
        User user = new User();
        user.setUserName(userRequest.getUserName());
        user.setPassword(userRequest.getUserName());
        userRepository.save(user);
    }

    public void deleteUser(Long userId){
        if (userRepository.findById(userId).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not exist");
        }
        userRepository.deleteById(userId);
    }

}
