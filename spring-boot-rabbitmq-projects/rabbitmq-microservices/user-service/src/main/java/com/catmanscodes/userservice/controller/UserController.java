package com.catmanscodes.userservice.controller;

import com.catmanscodes.userservice.dto.UserDto;
import com.catmanscodes.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PostMapping("/email")
    public ResponseEntity<UserDto> userEmail(@RequestBody UserDto userDto) {

        logger.info("inside userEmail()");

        return ResponseEntity.ok(userService.sendEmail(userDto));
    }

    @PostMapping("/sms")
    public ResponseEntity<UserDto> userSMS(@RequestBody UserDto userDto) {

        logger.info("inside userSMS()");

        return ResponseEntity.ok(userService.sendSMS(userDto));
    }
}
