package com.ironhack.WTWAPI.service.impl;

import com.ironhack.WTWAPI.model.Role;
import com.ironhack.WTWAPI.model.User;
import com.ironhack.WTWAPI.model.WatchList;
import com.ironhack.WTWAPI.repository.RoleRepository;
import com.ironhack.WTWAPI.repository.UserRepository;
import com.ironhack.WTWAPI.repository.WatchListRepository;
import com.ironhack.WTWAPI.service.interfaces.RoleServiceInterface;
import com.ironhack.WTWAPI.service.interfaces.UserServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@Slf4j
public class UserService implements UserServiceInterface, UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleServiceInterface roleService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private WatchListRepository watchListRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        // Handle possible errors:
        if(userRepository.findByUsername(user.getUsername()).isPresent()) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "This username already exists,please try a different one" ); }
        if(userRepository.findByEmail(user.getEmail()).isPresent()) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "This email is already registered, try to log in" ); }
        // Encrypt secret key:
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Save new user:
        log.info("Saving a new user {} in the DB", user.getUsername());
        User newUser = new User(user.getName(), user.getUsername(), user.getEmail(), user.getPassword());
        User dbUser = userRepository.save(newUser);
        // Add USER role to user:
        Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
        if(userRole.isEmpty()) {
            log.info("Saving a new role ROLE_USER in the DB");
            roleService.saveRole(new Role("ROLE_USER"));
        }
        roleService.addRoleToUser(user.getEmail(), "ROLE_USER");
        return dbUser;
    }

    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
    public Set<User> getUserByNameOrEmail(String string) {
        log.info("Fetching users containing {}, string");
        Set<User> users = new HashSet<>();
        users.addAll(userRepository.findByUsernameContaining(string));
        users.addAll(userRepository.findByEmailStartingWith(string));
        // Handle possible errors:
        if(users.size() == 0) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "No elements to show" ); }
        // Return results
        return users;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).get();
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User is found in the database: {}", email);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
        }
    }

    public void follow(Long followerId, Long userToFollowId) {
        Optional<User> follower = userRepository.findById(followerId);
        Optional<User> userToFollow = userRepository.findById(userToFollowId);
        // Handle possible errors:
        if(follower.isEmpty()) { throw new ResponseStatusException( HttpStatus.NOT_FOUND, "User not found" ); }
        if(userToFollow.isEmpty()) { throw new ResponseStatusException( HttpStatus.NOT_FOUND, "List not found" ); }
        if(userToFollow.get().getFollowers().contains(follower.get())) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "Oops, this user already is a follower!" ); }
        // Modify user's set of followers:
        userToFollow.get().getFollowers().add(follower.get());
        // Save modified user
        userRepository.save(userToFollow.get());
    }
    public void unfollow(Long followerId, Long userToFollowId) {
        Optional<User> follower = userRepository.findById(followerId);
        Optional<User> userToFollow = userRepository.findById(userToFollowId);
        // Handle possible errors:
        if(follower.isEmpty()) { throw new ResponseStatusException( HttpStatus.NOT_FOUND, "User not found" ); }
        if(userToFollow.isEmpty()) { throw new ResponseStatusException( HttpStatus.NOT_FOUND, "List not found" ); }
        // Modify user's set of followers:
        userToFollow.get().getFollowers().remove(follower.get());
        // Save modified user
        userRepository.save(userToFollow.get());
    }

    public User updateUser(Long id, User user) {
        User userFromDB = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setId(userFromDB.getId());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User userFromDB = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        userRepository.deleteById(id);
    }




}
