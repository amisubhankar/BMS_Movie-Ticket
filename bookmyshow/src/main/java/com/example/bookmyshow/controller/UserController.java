package com.example.bookmyshow.controller;

import com.example.bookmyshow.dtos.ResponseStatus;
import com.example.bookmyshow.dtos.SignUpRequestDTO;
import com.example.bookmyshow.dtos.SignUpResponseDTO;
import com.example.bookmyshow.models.User;
import com.example.bookmyshow.services.UserService;
import com.sun.net.httpserver.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    public SignUpResponseDTO signUp(SignUpRequestDTO request){
        User user = userService.signUp(request.getEmail(), request.getPassword());
        return new SignUpResponseDTO(user.getId(), ResponseStatus.SUCCESS);
    }

    public boolean login(String email, String password){
        return userService.login(email,password);
    }
}
