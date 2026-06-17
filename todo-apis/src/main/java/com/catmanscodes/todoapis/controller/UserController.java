package com.catmanscodes.todoapis.controller;

import com.catmanscodes.todoapis.dto.JwtResponse;
import com.catmanscodes.todoapis.dto.LoginUserDto;
import com.catmanscodes.todoapis.dto.RegisterUserDto;
import com.catmanscodes.todoapis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserDto registerUserDto) {

        userService.registerNewUser(registerUserDto);

        return ResponseEntity.ok("Register User");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody LoginUserDto loginUserDto) {
        String token = userService.loginUser(loginUserDto);

        JwtResponse jwtResponse = new JwtResponse();

        System.out.println("token: ==> " + token);
        jwtResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtResponse);
    }

}
