package com.example.bookmyshow;

import com.example.bookmyshow.controller.UserController;
import com.example.bookmyshow.dtos.SignUpRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private UserController userController;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
//        signUpRequestDTO.setEmail("babi@gmail.com");
//        signUpRequestDTO.setPassword("S@21");
//
//        userController.signUp(signUpRequestDTO);

        System.out.println(userController.login("babi@gmail.com", "S@21"));

    }
}
