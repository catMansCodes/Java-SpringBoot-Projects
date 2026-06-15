package com.catmanscodes.userapis.utils;

import com.catmanscodes.userapis.dto.UserDto;
import com.catmanscodes.userapis.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUserEntity(UserDto userDto) {
        return new User(
                userDto.id(),
                userDto.firstName(),
                userDto.lastName(),
                userDto.email()
        );
    }

    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }


}
