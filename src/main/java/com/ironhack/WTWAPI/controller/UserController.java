package com.ironhack.WTWAPI.controller;

import com.ironhack.WTWAPI.model.User;
import com.ironhack.WTWAPI.service.interfaces.UserServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("api")
@Slf4j
public class UserController {

    @Autowired
    private UserServiceInterface userService;

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable(name = "id") Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/users/search/{string}")
    @ResponseStatus(HttpStatus.OK)
    public Set<User> getUserByNameOrEmail(@PathVariable(name = "string") String string) {
        return userService.getUserByNameOrEmail(string);
    }
}
