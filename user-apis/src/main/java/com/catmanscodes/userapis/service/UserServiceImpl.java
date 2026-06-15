package com.catmanscodes.userapis.service;

import com.catmanscodes.userapis.dto.UserDto;
import com.catmanscodes.userapis.exception.ResourceNotFoundException;
import com.catmanscodes.userapis.model.User;
import com.catmanscodes.userapis.repository.UserRepository;
import com.catmanscodes.userapis.utils.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = this.userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        if (!userList.isEmpty()) {
            userList.forEach(user -> {
                userDtoList.add(userMapper.toUserDto(user));
            });
        }

        return userDtoList;
    }

    @Override
    public UserDto findById(Integer id) {

        User user = this.userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not founded with id :" + id)
        );

        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User userEntity = this.userMapper.toUserEntity(userDto);
        User savedUser = this.userRepository.save(userEntity);

        return this.userMapper.toUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {

        User user = this.userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not founded with id :" + id)
        );

        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setEmail(userDto.email());

        this.userRepository.save(user);

        return userDto;
    }

    @Override
    public void deleteUser(Integer id) {
        this.userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not founded with id :" + id)
        );

        this.userRepository.deleteById(id);
    }
}
