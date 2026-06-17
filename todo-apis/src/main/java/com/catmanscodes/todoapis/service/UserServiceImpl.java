package com.catmanscodes.todoapis.service;

import com.catmanscodes.todoapis.dto.LoginUserDto;
import com.catmanscodes.todoapis.dto.RegisterUserDto;
import com.catmanscodes.todoapis.entity.Role;
import com.catmanscodes.todoapis.entity.User;
import com.catmanscodes.todoapis.exception.TodoAPIException;
import com.catmanscodes.todoapis.repository.RoleRepository;
import com.catmanscodes.todoapis.repository.UserRepository;
import com.catmanscodes.todoapis.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void registerNewUser(RegisterUserDto registerUserDto) {

        boolean isExistByUserName = userRepository.existsByUsername(registerUserDto.userName());
        boolean isExistByEmail = userRepository.existsByEmail(registerUserDto.email());

        if (isExistByUserName) {
            throw new TodoAPIException("Username already exists", HttpStatus.BAD_REQUEST);
        }

        if (isExistByEmail) {
            throw new TodoAPIException("Username already exists", HttpStatus.BAD_REQUEST);
        }

        User newUser = new User();

        newUser.setUsername(registerUserDto.userName());
        newUser.setEmail(registerUserDto.email());
        newUser.setPassword(passwordEncoder.encode(registerUserDto.password()));
        newUser.setName(registerUserDto.name());

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_USER");
        roles.add(role);

        newUser.setRoles(roles);

        userRepository.save(newUser);
    }

    @Override
    public String loginUser(LoginUserDto loginUserDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.emailOrUserName(),
                        loginUserDto.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return jwtTokenProvider.generateToken(authenticate);
    }


}
