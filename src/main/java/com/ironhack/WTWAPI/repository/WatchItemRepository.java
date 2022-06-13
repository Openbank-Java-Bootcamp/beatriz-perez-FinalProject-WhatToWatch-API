package com.ironhack.WTWAPI.repository;

import com.ironhack.WTWAPI.model.User;
import com.ironhack.WTWAPI.model.WatchItem;
import com.ironhack.WTWAPI.model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchItemRepository extends JpaRepository<WatchItem,Long> {
    Optional<WatchItem> findByImdbId(String IMDbId);
    List<WatchItem> findAllByType(String type);
}

