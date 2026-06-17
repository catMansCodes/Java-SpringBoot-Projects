package com.catmanscodes.todoapis.dto;

public record RegisterUserDto(
        String name,
        String userName,
        String password,
        String email) {
}
