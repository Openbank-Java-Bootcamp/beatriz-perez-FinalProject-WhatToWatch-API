package com.ironhack.WTWAPI.service.impl;

import com.ironhack.WTWAPI.model.Role;
import com.ironhack.WTWAPI.model.User;
import com.ironhack.WTWAPI.repository.RoleRepository;
import com.ironhack.WTWAPI.repository.UserRepository;
import com.ironhack.WTWAPI.service.interfaces.RoleServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoleService implements RoleServiceInterface {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Role> getAllRoles() {
        // Handle error:
        if(roleRepository.findAll().size() == 0) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "No elements to show" ); }
        // Show results
        log.info("Fetching all existing roles");
        return roleRepository.findAll();
    }

    public Role saveRole(Role role) {
        // Handle possible errors:
        if(roleRepository.findByName(role.getName()).isPresent()) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "Element already exists" ); }
        // Save new role:
        log.info("Saving a new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    public void addRoleToUser(String email, String roleName) {
        Optional<User> user = userRepository.findByEmail(email);
        Optional<Role> role = roleRepository.findByName(roleName);
        // Handle possible errors:
        if(user.isEmpty()) { throw new ResponseStatusException( HttpStatus.NOT_FOUND, "User not found" ); }
        if(role.isEmpty()) { throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Role not found" ); }
        // Modify user's collection of roles:
        user.get().getRoles().add(role.get());
        // Save modified user
        userRepository.save(user.get());
    }
}