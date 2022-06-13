package com.ironhack.WTWAPI.service.interfaces;

import com.ironhack.WTWAPI.model.User;
import java.util.List;
import java.util.Set;

public interface UserServiceInterface {
    User saveUser(User userSignupDTO);
    List<User> getUsers();
    User getUserById(Long userId);
    Set<User> getUserByNameOrEmail(String string);

    void addUserToWatchListParticipants(Long userId, Long WatchListId);
}
