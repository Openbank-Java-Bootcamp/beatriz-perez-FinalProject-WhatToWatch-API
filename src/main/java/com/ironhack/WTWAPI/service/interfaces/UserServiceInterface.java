package com.ironhack.WTWAPI.service.interfaces;

import com.ironhack.WTWAPI.model.User;
import java.util.List;

public interface UserServiceInterface {
    User saveUser(User userSignupDTO);
    List<User> getUsers();

    User getUserById(Long userId);
}
