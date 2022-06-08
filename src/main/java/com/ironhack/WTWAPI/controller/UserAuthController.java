package com.ironhack.WTWAPI.controller;

import com.google.gson.Gson;
import com.ironhack.WTWAPI.DTO.UserVerifyDTO;
import com.ironhack.WTWAPI.model.User;
import com.ironhack.WTWAPI.repository.UserRepository;
import com.ironhack.WTWAPI.service.impl.UserService;
import com.ironhack.WTWAPI.service.interfaces.UserServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class UserAuthController {
    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody @Valid User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public String verifyToken(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        User userFromDb = userRepository.findByEmail(email);
        UserVerifyDTO userVerifyDTO = new UserVerifyDTO(userFromDb.getUsername(), userFromDb.getImageUrl());
        Gson gson = new Gson();
        String userDetails = gson.toJson(userVerifyDTO);
        return userDetails;
    }
}
