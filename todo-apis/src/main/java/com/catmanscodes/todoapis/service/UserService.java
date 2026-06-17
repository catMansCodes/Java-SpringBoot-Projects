package com.catmanscodes.todoapis.service;

import com.catmanscodes.todoapis.dto.LoginUserDto;
import com.catmanscodes.todoapis.dto.RegisterUserDto;

public interface UserService {
    void registerNewUser(RegisterUserDto registerUserDto);

    String loginUser(LoginUserDto loginUserDto);
}
