package com.ironhack.WTWAPI.service.interfaces;

import com.ironhack.WTWAPI.model.User;
import java.util.List;
import java.util.Set;

public interface UserServiceInterface {
    User saveUser(User userSignupDTO);
    List<User> getUsers();
    User getUserById(Long userId);
    Set<User> getUserByNameOrEmail(String string);

    void follow(Long followerId, Long userToFollowId);
    void unfollow(Long followerId, Long userToFollowId);

    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
