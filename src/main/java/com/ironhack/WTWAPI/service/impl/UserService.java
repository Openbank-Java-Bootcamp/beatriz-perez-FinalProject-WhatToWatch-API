package com.ironhack.WTWAPI.service.impl;

import com.ironhack.WTWAPI.model.Role;
import com.ironhack.WTWAPI.model.User;
import com.ironhack.WTWAPI.repository.RoleRepository;
import com.ironhack.WTWAPI.repository.UserRepository;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
}
