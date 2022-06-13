package com.ironhack.WTWAPI.repository;

import com.ironhack.WTWAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    List<User> findByEmailStartingWith(String email);
    Optional<User> findByUsername(String username);
    List<User> findByUsernameContaining(String username);
}
