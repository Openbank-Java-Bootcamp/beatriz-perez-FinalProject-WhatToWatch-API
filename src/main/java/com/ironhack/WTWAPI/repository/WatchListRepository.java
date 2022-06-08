package com.ironhack.WTWAPI.repository;

import com.ironhack.WTWAPI.model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchListRepository extends JpaRepository<WatchList,Long> {
}

