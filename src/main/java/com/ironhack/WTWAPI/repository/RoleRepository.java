package com.ironhack.WTWAPI.repository;

import com.ironhack.WTWAPI.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
