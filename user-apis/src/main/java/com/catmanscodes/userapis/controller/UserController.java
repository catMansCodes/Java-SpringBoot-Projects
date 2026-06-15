package com.catmanscodes.userapis.controller;

import com.catmanscodes.userapis.dto.UserDto;
import com.catmanscodes.userapis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 1. Get all users

    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUsers() {

        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    // 2. View User - get user by id

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(this.userService.findById(id), HttpStatus.OK);
    }

    // 3. Add new User - register

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {

        return new ResponseEntity<>(this.userService.createUser(userDto), HttpStatus.CREATED);
    }

    //4. Update User

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
        return new ResponseEntity<>(this.userService.updateUser(userDto, id), HttpStatus.OK);
    }

    // 5. Delete User

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {

        this.userService.deleteUser(id);

        return new ResponseEntity<>("User has been deleted successfully", HttpStatus.NO_CONTENT);
    }

}
