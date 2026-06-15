package com.catmanscodes.userapis.service;

import com.catmanscodes.userapis.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto findById(Integer id);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Integer id);

    void deleteUser(Integer id);

}
