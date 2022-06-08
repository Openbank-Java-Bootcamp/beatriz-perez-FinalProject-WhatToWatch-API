package com.ironhack.WTWAPI.service.interfaces;

import com.ironhack.WTWAPI.model.Role;

public interface RoleServiceInterface {
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
}
