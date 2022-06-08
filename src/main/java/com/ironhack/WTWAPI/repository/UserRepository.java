package com.ironhack.WTWAPI.repository;

import com.ironhack.WTWAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
