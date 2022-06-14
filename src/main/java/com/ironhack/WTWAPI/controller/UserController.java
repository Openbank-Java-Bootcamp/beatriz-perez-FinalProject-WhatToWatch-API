package com.ironhack.WTWAPI.controller;

import com.ironhack.WTWAPI.DTO.IdOnlyDTO;
import com.ironhack.WTWAPI.model.User;
import com.ironhack.WTWAPI.model.WatchList;
import com.ironhack.WTWAPI.service.interfaces.UserServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public User getUserById(@PathVariable(name = "id") String userId) {
        return userService.getUserById(Long.parseLong(userId));
    }

    @GetMapping("/users/search/{string}")
    @ResponseStatus(HttpStatus.OK)
    public Set<User> getUserByNameOrEmail(@PathVariable(name = "string") String string) {
        return userService.getUserByNameOrEmail(string);
    }

    @PatchMapping("/users/follow/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void follow(@RequestBody @Valid IdOnlyDTO itemDTO, @PathVariable(name = "id") String userId) {
        userService.follow(itemDTO.getId(), Long.parseLong(userId));
    }
    @PatchMapping("/users/unfollow/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unfollow(@RequestBody @Valid IdOnlyDTO itemDTO, @PathVariable(name = "id") String userId) {
        userService.unfollow(itemDTO.getId(), Long.parseLong(userId));
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public User updateUser(@PathVariable Long id, @RequestBody @Valid User user){
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }


}
