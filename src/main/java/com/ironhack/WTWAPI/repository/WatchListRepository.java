package com.ironhack.WTWAPI.repository;

import com.ironhack.WTWAPI.model.User;
import com.ironhack.WTWAPI.model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchListRepository extends JpaRepository<WatchList,Long> {
    List<WatchList> findAllByOwner(User owner);
    Optional<WatchList> findByNameAndOwner(String name, User owner);

    List<WatchList> findAllByNameContaining(String name);

}

