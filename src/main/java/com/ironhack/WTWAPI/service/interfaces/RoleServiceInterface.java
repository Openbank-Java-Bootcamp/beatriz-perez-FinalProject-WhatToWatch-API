package com.ironhack.WTWAPI.service.interfaces;

import com.ironhack.WTWAPI.model.Role;

import java.util.List;

public interface RoleServiceInterface {
    List<Role> getAllRoles();
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
}
